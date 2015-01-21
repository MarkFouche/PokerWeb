package models.database;

/**
 * Created by Mark on 2015-01-18.
 */
import models.cards.Hand;

import javax.persistence.*;

@Entity
public class PokerHand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pokerHandId;
    private String hand;
    private String playerName;
    @ManyToOne
    @JoinColumn(name = "gameId")
    private Game game;

    public PokerHand() {}

    public long getPokerHandId() {
        return pokerHandId;
    }
    public void setPokerHandId(long pokerHandId) {
        this.pokerHandId = pokerHandId;
    }
    public String getHand() {
        return hand;
    }
    public void setHand(String hand) {
        this.hand = hand;
    }
    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    public Game getGame() {
        return game;
    }
    public void setGame(Game game) {
        this.game = game;
    }
}
