package models.imageFinder;

import models.cards.Card;
import models.cards.Rank;
import models.cards.Suit;

/**
 * Created by Mark on 2015-01-12.
 */
public final class CardImageFinder {
    private static String IMAGE_URL_START = "https://www.random.org/playing-cards/";
    private static String IMAGE_URL_END = ".png";
    private static int SERVER_CLUBS = 1;
    private static int SERVER_SPADES = 2;
    private static int SERVER_HEARTS = 3;
    private static int SERVER_DIAMONDS = 4;

    public static String getCardURL(Card card) {
        int cardNumber = getCardNumberFromCard(card);
        return IMAGE_URL_START + cardNumber + IMAGE_URL_END;
    }

    private static int getCardNumberFromCard(Card card) {
        int rank = getConvertedRank(card.getRank());
        int suit = getConvertedSuit(card.getSuit());

        return suit + rank * 4;
    }

    private static int getConvertedRank(Rank rank) {
        if (rank == Rank.ACE) {
            return 0;
        } else {
            return 13 - rank.ordinal();
        }
    }

    private static int getConvertedSuit(Suit suit) {
        switch (suit) {
            case CLUBS: return SERVER_CLUBS;
            case DIAMONDS: return SERVER_DIAMONDS;
            case HEARTS: return SERVER_HEARTS;
            case SPADES: return SERVER_SPADES;
            default: throw new IllegalArgumentException("Not a valid suit: " + suit);
        }
    }
}
