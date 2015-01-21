package models.cards;

/**
 * Created by Mark on 2015-01-09.
 */
public enum Suit {
    SPADES      ("S"),
    HEARTS      ("H"),
    DIAMONDS    ("D"),
    CLUBS       ("C");

    private final String symbol;

    private Suit(String suitSymbol) {
        this.symbol = suitSymbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Suit getSuitFromSymbol(String suitSymbol) {
        for (Suit suit: values()) {
            if (suitSymbol.equals(suit.getSymbol())) {
                return suit;
            }
        }

        throw new IllegalArgumentException("Not valid suit symbol: " + suitSymbol);
    }
}
