package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.cards.Hand;
import models.database.Game;
import models.database.PokerHand;
import models.views.GameView;
import repositories.IDatabaseAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mark on 2015-01-23.
 */

@Singleton
public class GameService implements IGameService {
    @Inject
    private IPokerService pokerService;

    @Inject
    private IDatabaseAdapter database;

    private List<Game> games = new ArrayList<>();

    private Game getHostedGame(String hostName) {
        Game game = games.stream().filter(g -> g.getHostName().equals(hostName)).findFirst().get();
        if (game != null) {
            return game;
        }
        else throw new RuntimeException("Game does not exist with host name:" + hostName);
    }

    @Override
    public void createGame(String hostName) {
        if (isUserHostingAGame(hostName)) {
            games.remove(getHostedGame(hostName));
        }
        Game game = new Game();
        game.setHostName(hostName);
        game.addPlayerName(hostName);
        games.add(game);
    }

    @Override
    public boolean isUserHostingAGame(String userName) {
        return games.stream().anyMatch(g -> g.getHostName().equals(userName));
    }

    @Override
    public List<String> getUsersWithOpenGames() {
        return games.stream().filter(g -> !g.isGameActive()).map(g -> g.getHostName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getUsersWithSpectatorGames() {
        return games.stream().filter(g -> g.isGameActive()).map(g -> g.getHostName()).collect(Collectors.toList());
    }

    @Override
    public List<String> getUsersInGame(String hostName) {
        return getHostedGame(hostName).getPlayerNames();
    }

    @Override
    public void addPlayerToGame(String playerName, String hostName) {
        if (!isPlayerInGame(playerName, hostName)) {
            getHostedGame(hostName).addPlayerName(playerName);
        }
    }

    @Override
    public boolean isPlayerInGame(String playerName, String hostName) {
        List<String> playerNames = getHostedGame(hostName).getPlayerNames();
        return playerNames.stream().anyMatch(p -> p.equals(playerName));
    }

    @Override
    public void dealHands(String hostName) {
        Game game = getHostedGame(hostName);
        List<String> playerNames = game.getPlayerNames();
        int numOfPlayers = playerNames.size();
        List<Hand> hands = pokerService.dealHands(numOfPlayers);
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
    }

    @Override
    public void addGameToDatabase(String hostName) {
        Game game = getHostedGame(hostName);
        database.addGameToDatabase(game);
    }

    @Override
    public GameView getGameView(String hostName) {
        return pokerService.generateGameView(getHostedGame(hostName));
    }

    @Override
    public boolean isGameActive(String hostName) {
        return getHostedGame(hostName).isGameActive();
    }
}
