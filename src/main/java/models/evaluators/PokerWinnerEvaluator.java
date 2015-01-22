package models.evaluators;

import models.cards.Hand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 2015-01-22.
 */
public final class PokerWinnerEvaluator {

    public static List<Integer> getWinnerPositions(List<Hand> hands) {
        List<Integer> winners = new ArrayList<>();
        List<HandRank> handRanks = new ArrayList<>();

        for (Hand hand: hands) {
            handRanks.add(HandEvaluator.getHandRank(hand));
        }

        boolean winnerFound = false;
        int currentHandRank = HandRank.STRAIGHT_FLUSH.ordinal();
        while(!winnerFound && currentHandRank >= 0) {
            for (int i = 0; i < handRanks.size(); i++) {
                if (handRanks.get(i).ordinal() == currentHandRank) {
                    winnerFound = true;
                    winners.add(i);
                }
            }
            currentHandRank -= 1;
        }

        return winners;
    }
}
