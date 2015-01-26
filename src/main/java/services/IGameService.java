package services;

import models.cards.Hand;
import models.database.Game;
import models.database.PokerHand;
import models.views.GameView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mark on 2015-01-26.
 */
public interface IGameService {
    public void createGame(String hostName);
    public boolean isUserHostingAGame(String userName);
    public List<String> getUsersWithOpenGames();
    public List<String> getUsersWithSpectatorGames();
    public List<String> getUsersInGame(String hostName);
    public void addPlayerToGame(String playerName, String hostName);
    public boolean isPlayerInGame(String playerName, String hostName);
    public void dealHands(String hostName);
    public void addGameToDatabase(String hostName);
    public GameView getGameView(String hostName);
    public boolean isGameActive(String hostName);
}
