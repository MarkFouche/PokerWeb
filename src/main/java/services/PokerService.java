package services;

import com.google.inject.Singleton;
import models.cards.Deck;
import models.cards.Hand;
import models.database.Game;
import models.database.PokerHand;
import models.evaluators.HandEvaluator;
import models.evaluators.PokerWinnerEvaluator;
import models.views.GameView;
import models.views.HandView;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by Mark on 2015-01-12.
 */

@Singleton
public class PokerService implements IPokerService {
    private Deck deck = new Deck();

    @Override
    public List<Hand> dealHands(int numHands) {
        deck.shuffleDeck(new Random().nextInt());
        List<Hand> hands = deck.getHands(numHands);
        return hands;
    }

    @Override
    public String evaluateHand(Hand hand) {
        return HandEvaluator.getHandRank(hand).toString();
    }

    @Override
    public List<String> generatePlayerNames(String userName, int numComputerNames) {
        List<String> names = new ArrayList<>();

        for(int i = 0; i <= numComputerNames; i++) {
            String playerName = i == 0 ? userName : "Computer "+i;
            names.add(playerName);
        }

        return names;
    }

    @Override
    public List<HandView> generateHandViews(List<Hand> hands, List<String> playerNames) {
        List<HandView> handViews = new ArrayList<>();

        for (int i = 0; i < hands.size(); i++) {
            HandView handView = new HandView(playerNames.get(i), evaluateHand(hands.get(i)), hands.get(i));
            handViews.add(handView);
        }

        return handViews;
    }

    @Override
    public GameView generateGameView(Game game) {
        GameView gameView = new GameView();

        // set timestamp
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss (dd MMM yyyy)");
        gameView.timestamp = simpleDateFormat.format(game.getTimestamp().getTime());

        // set handViews
        gameView.handViews = new ArrayList<>();
        for (PokerHand pokerHand: game.getPokerHands()) {
            Hand hand = new Hand(pokerHand.getHand());
            String playerName = pokerHand.getPlayerName();
            String handStrength = evaluateHand(hand);
            HandView handView = new HandView(playerName, handStrength, hand);
            gameView.handViews.add(handView);
        }

        // set winners
        List<Hand> hands = game.getPokerHands().stream().map(h -> new Hand(h.getHand())).collect(Collectors.toList());
        List<Integer> winnerPositions = PokerWinnerEvaluator.getWinnerPositions(hands);
        gameView.namesOfWinners = new ArrayList<>();
        for (Integer winner: winnerPositions) {
            HandView winnerHandView = gameView.handViews.get(winner);
            winnerHandView.isWinner = true;
            gameView.namesOfWinners.add(winnerHandView.playerName);
        }

        return gameView;
    }

    @Override
    public Game generateGameDataForDatabase(List<Hand> hands, List<String> playerNames) {
        Game game = new Game();
        List<PokerHand> pokerHands = new ArrayList<>();

        for (int i = 0; i < hands.size(); i++) {
            PokerHand pokerHand = new PokerHand();
            pokerHand.setHand(hands.get(i).toString());
            pokerHand.setPlayerName(playerNames.get(i));
            pokerHand.setGame(game);
            pokerHands.add(pokerHand);
        }

        game.setPokerHands(pokerHands);
        game.setTimestamp(Calendar.getInstance());

        return game;
    }
}
