package models.cards;

/**
 * Created by Mark on 2015-01-09.
 */
public final class Card {
    private final Rank rank;
    private final Suit suit;

    public Card(String card) {
        String rankSymbol = card.substring(0, card.length()-1);
        String suitSymbol = card.substring(card.length()-1);
        rank = Rank.getRankFromSymbol(rankSymbol);
        suit = Suit.getSuitFromSymbol(suitSymbol);
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String toString() {
        return rank.getSymbol() + suit.getSymbol();
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }
}
