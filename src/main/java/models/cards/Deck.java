package models.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Mark on 2015-01-09.
 */
public final class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();
        for (Suit suit:Suit.values()) {
            for (Rank rank:Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    public void shuffleDeck(int seed) {
        Collections.shuffle(cards, new Random(seed));
    }

    public List<Hand> getHands(int amount) {
        if (amount > 10 || amount < 1) {
            throw new IllegalArgumentException("Must draw 1-10 hands. Currently trying to draw:" + amount);
        }

        List<Hand> hands = new ArrayList<Hand>();

        for (int a = 0; a < amount; a++) {
            List<Card> drawnCards = cards.stream().skip(a * 5).limit(5).collect(Collectors.toList());
            hands.add(new Hand(drawnCards.toArray(new Card[drawnCards.size()])));
        }

        return hands;
    }
}
