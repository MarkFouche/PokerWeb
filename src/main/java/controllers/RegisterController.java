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

@Singleton
public class RegisterController {
    @Inject
    IAuthService authService;

    @Inject
    Router router;

    public Result index() {
        Result result = Results.html();
        return result;
    }

    public Result postRegister(Context context, FlashScope flashScope) {
        String userName = context.getParameter("userName");
        String password = context.getParameter("password");

        if (authService.doesUserExist(userName)) {
            flashScope.error(userName+" already taken. Please choose another name.");
        } else {
            authService.registerUser(userName, password);
            flashScope.success(userName+" successfully registered. Go to login page to log in.");
        }

        return Results.redirect(router.getReverseRoute(RegisterController.class, "index"));
    }
}
