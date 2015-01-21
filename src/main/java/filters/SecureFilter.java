package filters;

import com.google.inject.Inject;
import controllers.LoginController;
import ninja.*;

/**
 * Created by Mark on 2015-01-16.
 */
public class SecureFilter implements Filter {

    @Inject
    Router router;

    @Override
    public Result filter(FilterChain chain, Context context) {
        if (context.getSession() == null
                || context.getSession().get("username") == null) {

            return Results.redirect(router.getReverseRoute(LoginController.class, "index"));

        } else {
            return chain.next(context);
        }
    }
}
