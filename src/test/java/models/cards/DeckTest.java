package models.cards;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeckTest {
    @Test
    public void shouldCreateDeckWith52UniqueCards() {
        Deck deck = new Deck();

        Set<Card> uniqueDeck = new HashSet<Card>(deck.getCards());

        Assert.assertTrue(uniqueDeck.size() == 52);
    }

    @Test
    public void shuffledDeckShouldNotBeEqualToNewDeck() {
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        deck2.shuffleDeck(100);
        List<Card> cards1 = deck1.getCards();
        List<Card> cards2 = deck2.getCards();

        Assert.assertNotEquals(cards1, cards2);
    }

    @Test
    public void shouldHave52UniqueCardsInShuffledDeck() {
        Deck deck = new Deck();

        deck.shuffleDeck(100);
        Set<Card> uniqueDeck = new HashSet<Card>(deck.getCards());

        Assert.assertTrue(uniqueDeck.size() == 52);
    }

    @Test
    public void shouldDrawFirstFiveCards() {
        Deck deck = new Deck();

        Hand hand = deck.getHands(1).get(0);

        Assert.assertTrue(hand.getCards().get(0).getRank() == Rank.ACE);
        Assert.assertTrue(hand.getCards().get(0).getSuit() == Suit.SPADES);
        Assert.assertTrue(hand.getCards().get(4).getRank() == Rank.FIVE);
        Assert.assertTrue(hand.getCards().get(4).getSuit() == Suit.SPADES);
    }

    @Test
    public void shouldDrawTwoUniqueHands() {
        Deck deck = new Deck();

        List<Hand> hands = deck.getHands(2);
        Set<Card> uniqueCards = new HashSet<Card>(hands.get(0).getCards());
        uniqueCards.addAll(hands.get(1).getCards());

        Assert.assertTrue(hands.size() == 2);
        Assert.assertTrue(uniqueCards.size() == 10);
    }
}