package services;

import com.google.inject.Singleton;
import models.cards.Deck;
import models.cards.Hand;
import models.database.Game;
import models.database.PokerHand;
import models.evaluators.HandEvaluator;
import models.views.GameView;
import models.views.HandView;

import java.text.SimpleDateFormat;
import java.util.*;

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
        if (HandEvaluator.isStraightFlush(hand)) {
            return "Straight Flush";
        } else if (HandEvaluator.isFourOfAKind(hand)) {
            return "Four of a Kind";
        } else if (HandEvaluator.isFullHouse(hand)) {
            return "Full House";
        } else if (HandEvaluator.isFlush(hand)) {
            return "Flush";
        } else if (HandEvaluator.isStraight(hand)) {
            return "Straight";
        } else if (HandEvaluator.isThreeOfAKind(hand)) {
            return "Three Of A Kind";
        } else if (HandEvaluator.isTwoPairs(hand)) {
            return "Two Pairs";
        } else if (HandEvaluator.isOnePair(hand)) {
            return "One Pair";
        }
        return "High Card";
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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss (dd MMM yyyy)");
        gameView.timestamp = simpleDateFormat.format(game.getTimestamp().getTime());

        gameView.handViews = new ArrayList<>();
        for (PokerHand pokerHand: game.getPokerHands()) {
            Hand hand = new Hand(pokerHand.getHand());
            String playerName = pokerHand.getPlayerName();
            String handStrength = evaluateHand(hand);
            HandView handView = new HandView(playerName, handStrength, hand);
            gameView.handViews.add(handView);
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
