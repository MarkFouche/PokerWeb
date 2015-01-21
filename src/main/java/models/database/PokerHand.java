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
    private Long id;
    private String hand;
    private String playerName;
    @ManyToOne
    private Game game;

    public PokerHand() {}

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
