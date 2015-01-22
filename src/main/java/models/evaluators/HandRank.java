package models.evaluators;

/**
 * Created by Mark on 2015-01-09.
 */
public enum HandRank {
    HIGH_CARD ("High card"),
    ONE_PAIR ("One pair"),
    TWO_PAIR ("Two pair"),
    THREE_OF_A_KIND ("Three of a kind"),
    STRAIGHT ("Straight"),
    FLUSH ("Flush"),
    FULL_HOUSE ("Full House"),
    FOUR_OF_A_KIND ("Four of a Kind"),
    STRAIGHT_FLUSH ("Straight Flush");

    private final String symbol;

    private HandRank(String handRankSymbol) {
        this.symbol = handRankSymbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
