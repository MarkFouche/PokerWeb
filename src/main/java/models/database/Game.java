package models.database;

/**
 * Created by Mark on 2015-01-18.
 */
import models.cards.Hand;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gameId;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestamp;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<PokerHand> pokerHands;

    public Game() {}

    public long getGameId() {
        return gameId;
    }
    public void setGameId(long gameId) {
        this.gameId = gameId;
    }
    public Calendar getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }
    public List<PokerHand> getPokerHands() {
        return pokerHands;
    }
    public void setPokerHands(List<PokerHand> pokerHands) {
        this.pokerHands = pokerHands;
    }
}
