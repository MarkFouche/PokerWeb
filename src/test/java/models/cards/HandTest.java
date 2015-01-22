package models.cards;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Mark on 2015-01-09.
 */
public class HandTest {
    @Before
    public void setup() {

    }

    @After
    public void cleanup() {

    }

    @Test
    public void shouldPrintSortedHand() {
        // 1.Setup
        Hand hand = new Hand("(5C, 3S, 8H, 9H, KD)");

        // 2. exec functionality
        String representation = hand.toString();

        // 3. Asset Expectation
        assertEquals("(3S, 5C, 8H, 9H, KD)", representation);

        // 4. Cleanup
    }
}
