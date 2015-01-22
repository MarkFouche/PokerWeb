package models.views;

import models.cards.Card;
import models.cards.Hand;
import models.imageFinder.CardImageFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2015-01-21.
 */
public class HandView {
    public String playerName = "no name";
    public String handStrength = "no hand strength";
    public boolean isWinner = false;
    public List<String> cardURLs = new ArrayList<>();

    public HandView (String playerName, String handStrength, Hand hand) {
        this.playerName = playerName;
        this.handStrength = handStrength;
        for (Card card:hand.getCards()) {
            cardURLs.add(CardImageFinder.getCardURL(card));
        }
    }
}
