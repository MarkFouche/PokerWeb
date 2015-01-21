package services;

import models.cards.Hand;

import java.util.List;

/**
 * Created by Mark on 2015-01-12.
 */
public interface IPokerService {
    public List<Hand> dealHands(int numHands);
    public String evaluateHand(Hand hand);
}
