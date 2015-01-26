package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.views.GameView;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.Router;
import ninja.session.FlashScope;
import repositories.IDatabaseAdapter;
import services.IGameService;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Mark on 2015-01-23.
 */
@Singleton
//@FilterWith(SecureFilter.class)
public class GameController {

    @Inject
    private IDatabaseAdapter database;

    @Inject
    private IGameService gameService;

    @Inject
    private Router router;

    private volatile Calendar lastUpdateTime = Calendar.getInstance();

    public Result lobby(Context context) {
        String username = context.getSession().get("username");
        if (username == null) {
            username = "Guest(" + Math.round(Math.random() * 99999) + ")";
            context.getSession().put("username", username);
        }

        List<String> hostedGameNames = gameService.getUsersWithOpenGames();
        List<String> spectatorGames = gameService.getUsersWithSpectatorGames();

        Result result = Results.html().template("views/GameController/defaultLobby.ftl.html");
        result.render("hostedGameNames", hostedGameNames);
        result.render("spectatorGameNames", spectatorGames);
        result.render("username", username);
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
                throw new RuntimeException("updateLobby method in GameController.java failed to sleep");
            }
        }
        return lobby(context);
    }

    public Result host(Context context) {
        String username = context.getSession().get("username");
        context.getSession().put("hostname", username);

        gameService.createGame(username);
        List<String> playersInGame = gameService.getUsersInGame(username);
        lastUpdateTime = Calendar.getInstance();

        Result result = Results.html().template("views/GameController/singleGameLobby.ftl.html");
        result.render("hostname", username);
        result.render("username", username);
        result.render("players", playersInGame);
        return result;
    }

    public Result join(Context context) {
        String hostName = context.getParameter("hostname");

        if (hostName == null || !gameService.isUserHostingAGame(hostName)) {
            return Results.notFound();
        }

        context.getSession().put("hostname", hostName);

        if (gameService.isGameActive(hostName)) {
            return viewGame(context);
        }

        String userName = context.getSession().get("username");
        boolean isNewPlayer = !gameService.isPlayerInGame(userName, hostName);
        gameService.addPlayerToGame(userName, hostName);
        if (isNewPlayer) {
            lastUpdateTime = Calendar.getInstance();
        }
        List<String> playersInGame = gameService.getUsersInGame(hostName);

        System.out.println(playersInGame);
        Result result = Results.html().template("views/GameController/singleGameLobby.ftl.html");
        result.render("hostname", hostName);
        result.render("username", userName);
        result.render("players", playersInGame);
        return result;
    }

    public Result updateSingleGameLobby(Context context) {
        Calendar startTime = Calendar.getInstance();
        int timeout = 200;
        int currentWaitTime = 0;
        int maxWaitTime = 120000;
        while (startTime.compareTo(lastUpdateTime) > 0 && currentWaitTime < maxWaitTime) {
            System.out.println("Sleep: " + currentWaitTime);
            currentWaitTime += timeout;
            try {
                Thread.sleep(timeout);
            } catch (Exception e) {
                throw new RuntimeException("updateSingleGameLobby method in GameController.java failed to sleep");
            }
        }

        return join(context);
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
