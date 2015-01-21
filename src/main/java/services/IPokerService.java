package services;

import models.cards.Hand;
import models.database.Game;
import models.views.GameView;
import models.views.HandView;

import java.util.List;

/**
 * Created by Mark on 2015-01-12.
 */
public interface IPokerService {
    public List<Hand> dealHands(int numHands);
    public String evaluateHand(Hand hand);
    public List<String> generatePlayerNames(String userName, int numComputerNames);
    public List<HandView> generateHandViews(List<Hand> hands, List<String> playerNames);
    public Game generateGameDataForDatabase(List<Hand> hands, List<String> playerNames);
    public GameView generateGameView(Game game);
}
