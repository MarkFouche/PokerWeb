package models.evaluators;

import models.cards.Hand;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PokerWinnerEvaluatorTest {
    @Test
    public void shouldGetTwoWinners() {
        List<Hand> hands = new ArrayList<>();
        List<Integer> realWinnerPositions = new ArrayList<>();

        hands.add(new Hand("JD","10D","9D","QD","KD"));
        hands.add(new Hand("2D","2D","2H","2D","6D"));
        hands.add(new Hand("2D","2D","2H","6D","6D"));
        hands.add(new Hand("2D","3D","4D","9D","6D"));
        hands.add(new Hand("2D","3D","4D","5H","6D"));
        hands.add(new Hand("2D","2S","2H","QC","KD"));
        hands.add(new Hand("2D","2S","3H","3C","5D"));
        hands.add(new Hand("2D","2S","JH","QC","KD"));
        hands.add(new Hand("AD","2S","JH","QC","KD"));
        hands.add(new Hand("JD","10D","9D","QD","KD"));

        List<Integer> calculatedWinnerPositions = PokerWinnerEvaluator.getWinnerPositions(hands);

        Assert.assertTrue(calculatedWinnerPositions.size() == 2);
        Assert.assertTrue(calculatedWinnerPositions.get(0) == 0 || calculatedWinnerPositions.get(0) == 9);
        Assert.assertTrue(calculatedWinnerPositions.get(1) == 0 || calculatedWinnerPositions.get(1) == 9);
    }

}