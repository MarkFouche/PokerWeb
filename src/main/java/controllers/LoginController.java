package controllers;

/**
 * Created by Mark on 2015-01-16.
 */

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.Router;
import ninja.session.FlashScope;
import services.IAuthService;

import javax.mail.Session;

@Singleton
public class LoginController {
    @Inject
    IAuthService authService;

    @Inject
    Router router;

    public Result index() {
        return Results.html();
    }

    public Result postLogin(Context context, FlashScope flashScope) {
        String loginName = context.getParameter("userName");
        String password = context.getParameter("password");

        if (authService.isValidUser(loginName,password)) {
            context.getSession().put("username", loginName);
            return Results.redirect(router.getReverseRoute(ApplicationController.class, "index"));
        } else {
            flashScope.error("Invalid login name or password.");
            return Results.redirect(router.getReverseRoute(LoginController.class, "index"));
        }
    }

    public Result logout(Context context) {
        context.getSession().clear();
        return Results.redirect(router.getReverseRoute(LoginController.class, "index"));
    }
}
