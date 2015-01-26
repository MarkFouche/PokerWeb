/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import com.google.inject.Inject;
import filters.SecureFilter;
import models.database.Game;
import models.views.GameView;
import models.views.HandView;
import ninja.*;

import com.google.inject.Singleton;
import models.cards.Hand;
import repositories.IDatabaseAdapter;
import services.IPokerService;

import java.util.*;


@Singleton
@FilterWith(SecureFilter.class)
public class ApplicationController {

    @Inject
    private IPokerService pokerService;

    @Inject
    private Router router;

    @Inject
    private IDatabaseAdapter database;

    public Result index() {
        return Results.html();
    }

    public Result generateHands(Context context) {
        Result result = Results.html().template("views/ApplicationController/index.ftl.html");

        String userName = context.getSession().get("username");
        int numOfPlayers = Math.max(Math.min(Integer.parseInt(context.getParameter("numHands")), 6), 4);
        List<Hand> hands = pokerService.dealHands(numOfPlayers);

        List<String> playerNames = pokerService.generatePlayerNames(userName, numOfPlayers - 1);
        Game game = pokerService.generateGameDataForDatabase(hands, playerNames);

        database.addGameToDatabase(game);
        GameView gameView = pokerService.generateGameView(game);

        result.render("gameView", gameView);
        result.render("lastNumOfGeneratedHands", numOfPlayers);

        return result;
    }

    public Result history() {
        Result result = Results.html();

        List<Game> games = database.getGames();
        List<GameView> gameViewList = new ArrayList<>();

        for (Game game : games) {
            gameViewList.add(pokerService.generateGameView(game));
        }

        result.render("gameViews", gameViewList);

        return result;
    }

    public void setPokerService(IPokerService pokerService) {
        this.pokerService = pokerService;
    }
}
