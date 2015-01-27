package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.views.GameView;
import ninja.*;
import ninja.params.PathParam;
import ninja.session.FlashScope;
import repositories.IDatabaseAdapter;
import services.IGameService;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Mark on 2015-01-23.
 */
@Singleton
@FilterWith(SecureFilter.class)
public class GameController {
    @Inject
    private IGameService gameService;

    @Inject
    private Router router;

    private volatile Calendar lastUpdateTime = Calendar.getInstance();

    public Result hostGame(
            @PathParam("hostname") String hostName,
            Context context) {

        String username = hostName;
        context.getSession().put("hostname", username);

        gameService.createGame(username);
        lastUpdateTime = Calendar.getInstance();

        Result result = Results.redirect("/"+hostName+"/join_game");
        return result;
    }

    public Result updateLobby(Context context) {
        Calendar startTime = Calendar.getInstance();
        int timeout = 200;
        int currentWaitTime = 0;
        int maxWaitTime = 120000;
        while (startTime.compareTo(lastUpdateTime) > 0 && currentWaitTime < maxWaitTime) {
            currentWaitTime += timeout;
            try {
                Thread.sleep(timeout);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return getLobby(context);
    }

    public Result getLobby(Context context) {
        String username = context.getSession().get("username");

        List<String> hostedGameNames = gameService.getUsersWithOpenGames();
        List<String> spectatorGames = gameService.getUsersWithSpectatorGames();

        Result result = Results.html().template("views/GameController/defaultLobby.ftl.html");
        result.render("hostedGameNames", hostedGameNames);
        result.render("spectatorGameNames", spectatorGames);
        result.render("username", username);
        return result;
    }

    public Result updateSingleGameLobby(Context context) {
        String hostName = context.getSession().get("hostname");
        Calendar startTime = Calendar.getInstance();
        int timeout = 200;
        int currentWaitTime = 0;
        int maxWaitTime = 120000;
        while (startTime.compareTo(lastUpdateTime) > 0 && currentWaitTime < maxWaitTime) {
            currentWaitTime += timeout;
            try {
                Thread.sleep(timeout);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return joinGame(hostName, context);
    }

    public Result joinGame(
            @PathParam("hostname") String hostName,
            Context context) {

        String userName = context.getSession().get("username");

        if (hostName == null || !gameService.isUserHostingAGame(hostName)) {
            return Results.notFound();
        }

        context.getSession().put("hostname", hostName);

        if (gameService.isGameActive(hostName)) {
            return viewGame(context);
        }

        boolean isPlayerInGame = gameService.isPlayerInGame(userName, hostName);
        if (!isPlayerInGame) {
            gameService.addPlayerToGame(userName, hostName);
            lastUpdateTime = Calendar.getInstance();
            System.out.println("Host: "+hostName+"  User: "+userName);
        }
        List<String> playersInGame = gameService.getUsersInGame(hostName);

        Result result = Results.html().template("views/GameController/singleGameLobby.ftl.html");
        result.render("hostname", hostName);
        result.render("username", userName);
        result.render("players", playersInGame);
        return result;
    }

    public Result startGame(Context context, FlashScope flashScope) {
        String userName = context.getSession().get("username");
        String hostName = context.getSession().get("hostname");

        if (!userName.equals(hostName)) {
            return Results.notFound();
        }

        gameService.dealHands(hostName);
        gameService.addGameToDatabase(hostName);
        lastUpdateTime = Calendar.getInstance();

        return viewGame(context);
    }

    public Result viewGame(Context context) {
        String userName = context.getSession().get("username");
        String hostName = context.getSession().get("hostname");

        GameView gameView = gameService.getGameView(hostName);

        Result result = Results.html().template("views/GameController/game.ftl.html");
        result.render("gameView", gameView);
        result.render("hostname", hostName);
        result.render("username", userName);

        return result;
    }
}
