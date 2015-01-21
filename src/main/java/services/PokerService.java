package services;

import com.google.inject.Singleton;
import models.cards.Deck;
import models.cards.Hand;
import models.evaluators.HandEvaluator;

import java.util.List;
import java.util.Random;

/**
 * Created by Mark on 2015-01-12.
 */

@Singleton
public class PokerService implements IPokerService {
    private Deck deck = new Deck();

    @Override
    public List<Hand> dealHands(int numHands) {
        deck.shuffleDeck(new Random().nextInt());
        List<Hand> hands = deck.getHands(numHands);
        return hands;
    }

    @Override
    public String evaluateHand(Hand hand) {
        if (HandEvaluator.isStraightFlush(hand)) {
            return "Straight Flush";
        } else if (HandEvaluator.isFourOfAKind(hand)) {
            return "Four of a Kind";
        } else if (HandEvaluator.isFullHouse(hand)) {
            return "Full House";
        } else if (HandEvaluator.isFlush(hand)) {
            return "Flush";
        } else if (HandEvaluator.isStraight(hand)) {
            return "Straight";
        } else if (HandEvaluator.isThreeOfAKind(hand)) {
            return "Three Of A Kind";
        } else if (HandEvaluator.isTwoPairs(hand)) {
            return "Two Pairs";
        } else if (HandEvaluator.isOnePair(hand)) {
            return "One Pair";
        }
        return "High Card";
    }
}
