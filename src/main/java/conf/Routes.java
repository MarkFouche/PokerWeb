/**
 * Copyright (C) 2012 the original author or authors.
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

package conf;


import controllers.LoginController;
import controllers.RegisterController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {
        //router.GET().route("/hello_world.json").with(ApplicationController.class, "helloWorldJson");
        //router.GET().route("/").with(LoginController.class, "index");

        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");

        ///////////////////////////////////////////////////////////////////////
        // Other routes
        ///////////////////////////////////////////////////////////////////////
        router.POST().route("/register").with(RegisterController.class, "postRegister");
        router.POST().route("/login").with(LoginController.class, "postLogin");
        router.POST().route("/generate_hands").with(ApplicationController.class, "generateHands");

        router.GET().route("/logout").with(LoginController.class, "logout");
        router.GET().route("/hand").with(ApplicationController.class, "index");
        router.GET().route("/history").with(ApplicationController.class, "history");
        router.GET().route("/register").with(RegisterController.class, "index");
        router.GET().route("/login").with(LoginController.class, "index");

        ///////////////////////////////////////////////////////////////////////
        // Catch all
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
