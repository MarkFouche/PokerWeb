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
import services.GameService;
import services.IGameService;
import services.IPokerService;

import java.util.List;
import java.util.PrimitiveIterator;

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

    public Result lobby(Context context) {
        Result result = Results.html().template("views/GameController/defaultLobby.ftl.html");

        String username = context.getSession().get("username");
        if (username == null) {
            username = "Guest(" + Math.round(Math.random() * 99999) + ")";
            context.getSession().put("username", username);
        }

        List<String> hostedGameNames = gameService.getUsersWithOpenGames();
        List<String> spectatorGames = gameService.getUsersWithSpectatorGames();

        result.render("hostedGameNames", hostedGameNames);
        result.render("spectatorGameNames", spectatorGames);
        result.render("username", username);
        return result;
    }

    public Result host(Context context) {
        String username = context.getSession().get("username");
        context.getSession().put("hostname", username);

        gameService.createGame(username);
        List<String> playersInGame = gameService.getUsersInGame(username);

        Result result = Results.html().template("views/GameController/singleGameLobby.ftl.html");
        result.render("hostname", username);
        result.render("username", username);
        result.render("players", playersInGame);
        return result;
    }

    public Result join(Context context, FlashScope flashScope) {
        String hostName = context.getParameter("hostname");

        if (hostName == null || !gameService.isUserHostingAGame(hostName)) {
            return Results.notFound();
        }

        context.getSession().put("hostname", hostName);

        if (gameService.isGameActive(hostName)) {
            return viewGame(context);
        }

        String username = context.getSession().get("username");
        gameService.addPlayerToGame(username, hostName);
        List<String> playersInGame = gameService.getUsersInGame(hostName);

        Result result = Results.html().template("views/GameController/singleGameLobby.ftl.html");
        result.render("hostname", hostName);
        result.render("username", username);
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