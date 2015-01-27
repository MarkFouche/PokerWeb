package services;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameServiceTest {

    @Test
    public void shouldOnlyCreateOneGamePerUser ()  {
        GameService gameService = new GameService();

        String userName = "bob";

        gameService.createGame(userName);
        gameService.createGame(userName);
        gameService.createGame(userName);

        assertTrue(gameService.getUsersInGame(userName).size() == 1);
    }

    @Test
    public void shouldAddAllThePlayersInGame ()  {
        GameService gameService = new GameService();

        String user1 = "bob";
        String user2 = "bill";
        String user3 = "john";

        gameService.createGame(user1);
        gameService.addPlayerToGame(user2, user1);
        gameService.addPlayerToGame(user3, user1);
        gameService.addPlayerToGame(user2, user1);

        List<String> users = gameService.getUsersInGame(user1);

        assertTrue(gameService.isPlayerInGame(user1, user1));
        assertTrue(gameService.isPlayerInGame(user2, user1));
        assertTrue(gameService.isPlayerInGame(user3, user1));
        assertTrue(users.size() == 3);
    }
}