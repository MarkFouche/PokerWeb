package models.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Mark on 2015-01-09.
 */
public final class Hand {
    private final List<Card> hand;

    public Hand(String cardString) {
        this(getTokenizedCardStringArray(cardString));
    }

    private static String[] getTokenizedCardStringArray(String cardString) {
        String cleanCardString = cardString.replace("(","").replace(")","").replace(",","");
        StringTokenizer stringTokenizer = new StringTokenizer(cleanCardString);
        List<String> cardStrings = new ArrayList<>();
        while (stringTokenizer.hasMoreElements()) {
            cardStrings.add(stringTokenizer.nextElement().toString());
        }
        return cardStrings.toArray(new String[cardStrings.size()]);
    }

    public Hand(String ... cardStrings) {
        if (cardStrings.length != 5) {
            throw new IllegalArgumentException("Hand must have 5 cardString entries.");
        }

        hand = new ArrayList<Card>();
        for (String cardString : cardStrings) {
            Card card = new Card(cardString);
            hand.add(card);
        }
    }

    public Hand(Card ... cards) {
        if (cards.length != 5) {
            throw new IllegalArgumentException("Hand must have 5 card entries.");
        }

        hand = new ArrayList<Card>();
        for (Card card : cards) {
            hand.add(card);
        }
    }

    @Override
    public String toString() {
        // stringjoiner
        String returnString = ("(");
        for (int i = 0; i < hand.size(); i++) {
            returnString += hand.get(i).toString();
            if (i != hand.size() -1) {
                returnString += ", ";
            }
        }
        return returnString + ")";
    }

    public List<Card> getCards() {
        return hand;
    }
}
