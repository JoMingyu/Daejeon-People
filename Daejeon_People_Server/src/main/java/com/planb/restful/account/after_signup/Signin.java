package com.planb.restful.account.after_signup;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(name = "로그인", summary = "로그인")
@RESTful(requestBody = "id : String, password : String, keep_login : boolean", successCode = 201, responseHeaders = "Set-Cookie(key=UserSession)", failureCode = 204)
@Route(uri = "/signin", method = HttpMethod.POST)
public class Signin implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		UserManager userManager = new UserManager();
		String id = ctx.request().getFormAttribute("id");
		String password = ctx.request().getFormAttribute("password");
		boolean keepLogin = Boolean.parseBoolean(ctx.request().getFormAttribute("keep_login"));

		if (userManager.signin(id, password)) {
			userManager.registerSessionId(ctx, keepLogin, id);

			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}
