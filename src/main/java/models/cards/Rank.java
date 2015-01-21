package models.cards;

/**
 * Created by Mark on 2015-01-09.
 */
public enum Rank {
    ACE         ("A"),
    TWO         ("2"),
    THREE       ("3"),
    FOUR        ("4"),
    FIVE        ("5"),
    SIX         ("6"),
    SEVEN       ("7"),
    EIGHT       ("8"),
    NINE        ("9"),
    TEN         ("10"),
    JACK        ("J"),
    QUEEN       ("Q"),
    KING        ("K");

    private final String symbol;

    private Rank(String rankSymbol) {
        this.symbol = rankSymbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Rank getRankFromSymbol(String rankSymbol) {
        for (Rank rank: values()) {
            if (rankSymbol.equals(rank.getSymbol())) {
                return rank;
            }
        }

        throw new IllegalArgumentException("Not valid rank symbol: " + rankSymbol);
    }
}
