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
import models.views.HandView;
import ninja.*;

import com.google.inject.Singleton;
import models.cards.Card;
import models.cards.Hand;
import models.imageFinder.CardImageFinder;
import services.IPokerService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Singleton
@FilterWith(SecureFilter.class)
public class ApplicationController {

    @Inject
    private IPokerService pokerService;

    @Inject
    private Router router;

    public Result index(Context context) {
        Result result = Results.html();
        return result;
    }

    public Result generateHands(Context context) {
        Result result = Results.html().template("views/ApplicationController/index.ftl.html");

        int numHandsToGenerate = Integer.parseInt(context.getParameter("numHands"));
        List<Hand> hands = pokerService.dealHands(numHandsToGenerate);
        List<HandView> handViewList = new ArrayList<>();
        String userName;
        HandView handView;

        for (int i = 0; i < hands.size(); i++) {
            userName = i == 0 ? context.getSession().get("username") : "Computer "+i;
            handView = new HandView(userName, pokerService.evaluateHand(hands.get(i)), hands.get(i));
            handViewList.add(handView);
        }

        result.render("handViewList", handViewList);
        result.render("lastNumOfGeneratedHands", numHandsToGenerate);

        return result;
    }

    /*
    public Result helloWorldJson() {
        
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }
    
    public static class SimplePojo {

        public String content;
        
    }
*/
    public void setPokerService(IPokerService pokerService) {
        this.pokerService = pokerService;
    }
}
