package models.database;

/**
 * Created by Mark on 2015-01-18.
 */
import models.cards.Hand;

import javax.persistence.*;
import java.util.ArrayList;
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
    @Transient
    private String hostName;
    @Transient
    private List<String> playerNames = new ArrayList<>();
    @Transient
    private boolean gameActive = false;

    public Game() {}
    public Game(String hostName) {
        this.hostName = hostName;
        this.playerNames.add(hostName);
    }

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
        this.gameActive = true;
    }
    public String getHostName() {
        return hostName;
    }
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public List<String> getPlayerNames() {
        return new ArrayList<String>(playerNames);
    }
    public void addPlayerName (String playerName) {
        if (playerNames.stream().noneMatch(p -> p == playerName)) {
            playerNames.add(playerName);
        }
    }
    public boolean isGameActive() {
        return gameActive;
    }
}
