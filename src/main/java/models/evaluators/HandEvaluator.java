package models.evaluators;

import models.cards.Card;
import models.cards.Hand;
import models.cards.Rank;
import models.cards.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mark on 2015-01-09.
 */
public final class HandEvaluator {
    private static List<Integer> getListOfRankCounts(Hand hand) {
        List<Card> cards = hand.getCards();
        List<Rank> rankType = new ArrayList<Rank>();
        List<Integer> rankCounts = new ArrayList<Integer>();
        // Map <Rank, Integer>

        Collections.sort(hand.getCards(), new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.getRank().compareTo(card2.getRank());
            }
        });

        for (Card card:hand.getCards()) {
            int arrayIndex = rankType.size() - 1;
            if (rankType.isEmpty() || rankType.get(arrayIndex) != card.getRank()) {
                rankType.add(card.getRank());
                rankCounts.add(1);
            } else {
                rankCounts.set(arrayIndex, rankCounts.get(arrayIndex) + 1);
            }
        }

        return rankCounts;
    }

    public static boolean isStraightFlush (Hand hand) {
        List<Rank> ranks = hand.getCards().stream().map(c -> c.getRank()).collect(Collectors.toList());
        return hand.getCards().stream().allMatch(c -> c.getSuit() == hand.getCards().get(0).getSuit())
                && ranks.stream().mapToInt(r -> r.ordinal()).max().getAsInt() -
                    ranks.stream().mapToInt(r -> r.ordinal()).min().getAsInt() == 4
                && ranks.stream().distinct().count() == 5;

        //return isFlush(hand) && isStraight(hand);
    }

    public static boolean isFourOfAKind (Hand hand) {
        List<Integer> rankCounts = getListOfRankCounts(hand);

        return rankCounts.size() == 2 && (rankCounts.get(0) == 4 || rankCounts.get(1) == 4);
    }

    public static boolean isFullHouse (Hand hand) {
        /*
        // Imparative
        ArrayList<Integer> rankCounts = getListOfRankCounts(hand);
        return rankCounts.size() == 2 && (rankCounts.get(0) == 3 || rankCounts.get(1) == 3);
        */

        // Functional
        List<Rank> ranks = hand.getCards().stream().map(c -> c.getRank()).collect(Collectors.toList());
        long countOfFirstRank = ranks.stream().filter(r -> r == ranks.stream().findFirst().get()).count();
        boolean hasTwoTypesOfRanks = ranks.stream().distinct().count() == 2;

        return hasTwoTypesOfRanks && (countOfFirstRank == 2 || countOfFirstRank == 3);
    }

    public static boolean isFlush (Hand hand) {
        List<Card> cards = hand.getCards();
        Suit firstCardSuit = cards.get(0).getSuit();

        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getSuit() != firstCardSuit) {
                return false;
            }
        }

        return true;
    }

    public static boolean isStraight (Hand hand) {
        List<Card> cards = hand.getCards();

        Collections.sort(hand.getCards(), new Comparator<Card>() {
            @Override
            public int compare(Card card1, Card card2) {
                return card1.getRank().compareTo(card2.getRank());
            }
        });

        for (int i = 0; i < cards.size() -1; i++) {
            if (cards.get(i).getRank().ordinal() + 1 != cards.get(i+1).getRank().ordinal()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isThreeOfAKind (Hand hand) {
        List<Integer> rankCounts = getListOfRankCounts(hand);

        return rankCounts.size() == 3 && (rankCounts.get(0) == 3 || rankCounts.get(1) == 3 || rankCounts.get(2) == 3);
    }

    public static boolean isTwoPairs (Hand hand) {
        /*
        // Imparative
        ArrayList<Integer> rankCounts = getListOfRankCounts(hand);

        return rankCounts.size() == 3 && (rankCounts.get(0) == 2 || rankCounts.get(1) == 2 || rankCounts.get(2) == 2);
        */
        // Functional
        List<Rank> ranks = hand.getCards().stream().map(c -> c.getRank()).collect(Collectors.toList());
        long countOfFirstRank = ranks.stream().filter(r -> r == ranks.stream().findFirst().get()).count();
        long countOfSecondRank = ranks.stream().filter(r -> r == ranks.stream().skip(1).findFirst().get()).count();
        boolean hasThreeTypesOfRanks = ranks.stream().distinct().count() == 3;

        return hasThreeTypesOfRanks && (countOfFirstRank == 2 || countOfSecondRank == 2);
    }

    public static boolean isOnePair (Hand hand) {
        List<Integer> rankCounts = getListOfRankCounts(hand);

        return rankCounts.size() == 4 &&
                (rankCounts.get(0) == 2 || rankCounts.get(1) == 2 || rankCounts.get(2) == 2 || rankCounts.get(3) == 2);
    }
}
