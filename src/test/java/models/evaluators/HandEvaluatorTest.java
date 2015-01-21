package models.evaluators;


import org.junit.Assert;
import org.junit.Test;
import models.cards.Hand;

public class HandEvaluatorTest {

    @Test
    public void shouldMatchStraightFlush() {
        // 1.Setup
        Hand hand1 = new Hand("JD","10D","9D","QD","KD");
        Hand hand2 = new Hand("5H","2H","3H","4H","AH");

        // 2. exec functionality

        // 3. Asset Expectation
        Assert.assertTrue(HandEvaluator.isStraightFlush(hand1));
        Assert.assertTrue(HandEvaluator.isStraightFlush(hand2));

        // 4. Cleanup
    }

    @Test
    public void shouldNotMatchStraightFlush() {
        Hand hand1 = new Hand("QD","10D","JD","9D","KH");

        Assert.assertFalse(HandEvaluator.isStraightFlush(hand1));
    }

    @Test
    public void shouldMatchFourOfKind() {
        Hand hand1 = new Hand("2D","2S","2H","2C","3D");
        Hand hand2 = new Hand("KH","QD","QS","QC","QH");

        Assert.assertTrue(HandEvaluator.isFourOfAKind(hand1));
        Assert.assertTrue(HandEvaluator.isFourOfAKind(hand2));
    }

    @Test
    public void shouldNotMatchFourOfKind() {
        Hand hand1 = new Hand("2D","KD","JD","9D","2H");
        Hand hand2 = new Hand("2D","KD","JD","2C","2H");

        Assert.assertFalse(HandEvaluator.isFourOfAKind(hand1));
        Assert.assertFalse(HandEvaluator.isFourOfAKind(hand2));
    }


    @Test
    public void shouldMatchFullHouse() {
        Hand hand1 = new Hand("2D","2S","2H","3C","3D");
        Hand hand2 = new Hand("KH","KD","QS","QC","QH");

        Assert.assertTrue(HandEvaluator.isFullHouse(hand1));
        Assert.assertTrue(HandEvaluator.isFullHouse(hand2));
    }

    @Test
    public void shouldNotMatchFullHouse() {
        Hand hand1 = new Hand("2D","KD","KC","9C","2H");
        Hand hand2 = new Hand("2D","KD","JD","2C","2H");

        Assert.assertFalse(HandEvaluator.isFullHouse(hand1));
        Assert.assertFalse(HandEvaluator.isFullHouse(hand2));
    }

    @Test
    public void shouldMatchFlush() {
        // 1.Setup
        Hand hand1 = new Hand("JD","10D","9D","QD","KD");
        Hand hand2 = new Hand("5H","2H","3H","4H","AH");

        // 2. exec functionality

        // 3. Asset Expectation
        Assert.assertTrue(HandEvaluator.isStraight(hand1));
        Assert.assertTrue(HandEvaluator.isStraight(hand2));

        // 4. Cleanup
    }

    @Test
    public void shouldNotMatchFlush() {
        Hand hand1 = new Hand("QD","10D","JD","8D","KH");
        Hand hand2 = new Hand("QS","10D","JD","8D","KD");

        Assert.assertFalse(HandEvaluator.isStraight(hand1));
    }

    @Test
    public void shouldMatchStraight() {
        // 1.Setup
        Hand hand1 = new Hand("JD","10D","9S","QD","KD");
        Hand hand2 = new Hand("5H","2H","3S","4H","AH");

        // 2. exec functionality

        // 3. Asset Expectation
        Assert.assertTrue(HandEvaluator.isStraight(hand1));
        Assert.assertTrue(HandEvaluator.isStraight(hand2));

        // 4. Cleanup
    }

    @Test
    public void shouldNotMatchStraight() {
        Hand hand1 = new Hand("QD","10D","JD","8D","KH");

        Assert.assertFalse(HandEvaluator.isStraight(hand1));
    }

    @Test
    public void shouldMatchThreeOfKind() {
        Hand hand1 = new Hand("2D","2S","KH","3C","2D");
        Hand hand2 = new Hand("KH","QD","JS","QC","QH");

        Assert.assertTrue(HandEvaluator.isThreeOfAKind(hand1));
        Assert.assertTrue(HandEvaluator.isThreeOfAKind(hand2));
    }

    @Test
    public void shouldNotMatchFreeOfKind() {
        Hand hand1 = new Hand("2D","2S","2H","3C","2D");
        Hand hand2 = new Hand("2D","JD","JD","2C","2H");

        Assert.assertFalse(HandEvaluator.isThreeOfAKind(hand1));
        Assert.assertFalse(HandEvaluator.isThreeOfAKind(hand2));
    }

    @Test
    public void shouldMatchTwoPairs() {
        Hand hand1 = new Hand("KD","2S","2H","3C","3D");
        Hand hand2 = new Hand("KH","KD","JS","JC","QH");

        Assert.assertTrue(HandEvaluator.isTwoPairs(hand1));
        Assert.assertTrue(HandEvaluator.isTwoPairs(hand2));
    }

    @Test
    public void shouldNotMatchTwoPairs() {
        Hand hand1 = new Hand("2D","2S","2H","3C","3D");
        Hand hand2 = new Hand("2D","JD","JD","AC","3H");

        Assert.assertFalse(HandEvaluator.isTwoPairs(hand1));
        Assert.assertFalse(HandEvaluator.isTwoPairs(hand2));
    }

    @Test
    public void shouldMatchOnePair() {
        Hand hand1 = new Hand("2D","2S","4H","5C","3D");
        Hand hand2 = new Hand("AH","KD","JS","QC","QH");

        Assert.assertTrue(HandEvaluator.isOnePair(hand1));
        Assert.assertTrue(HandEvaluator.isOnePair(hand2));
    }

    @Test
    public void shouldNotMatchOnePair() {
        Hand hand1 = new Hand("2D","2S","2H","3C","3D");
        Hand hand2 = new Hand("2D","JD","JD","3C","3H");
        Hand hand3 = new Hand("2D","JD","AD","4C","3H");

        Assert.assertFalse(HandEvaluator.isOnePair(hand1));
        Assert.assertFalse(HandEvaluator.isOnePair(hand2));
        Assert.assertFalse(HandEvaluator.isOnePair(hand3));
    }
/*
    @Test
    public void example() {
        // List<Integer> input = Arrays.asList(1, 2, 3);
        // input.stream().map(x -> x + 2).forEach(System.out::println);
        //   map function on every item in list
        //   forEach does for every item in list
        //   System.out::println = reference to function to use

        IntStream stream = IntStream.iterate(0, x -> {
            System.out.println(x);
            return x + 1;
        });
        // IntStream stream = IntStream.iterate(0, x -> x + 1);
        //   IntStream = Steam<int>
        //   .allMatch
        //   .anyMatch
        stream.map(x -> x * 2) //g(x)
                .map (x -> x + 2) //f(g(x))
                .filter(x -> x > 10)
                .findFirst()
                .getAsInt();


        stream.limit(100).forEach(System.out::println);
        //infinate stream limited to first 100
    }
    */
}