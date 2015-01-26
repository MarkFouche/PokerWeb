package services;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameServiceTest {

    @Test
    public void shouldOnlyCreateOneGamePerUser ()  {
        GameService gameService = new GameService();

        String userName = "bob";

        gameService.createGame(userName);
        gameService.createGame(userName);
        gameService.createGame(userName);

        assertTrue(gameService.getUsersWithHostedGames().size() == 1);
    }
}