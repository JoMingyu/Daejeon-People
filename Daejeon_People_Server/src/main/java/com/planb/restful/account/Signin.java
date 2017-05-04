package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.UserAccountManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/signin", method = HttpMethod.POST)
public class Signin implements Handler<RoutingContext> {
	UserAccountManager userManager;

	public Signin() {
		userManager = new UserAccountManager();
	}

	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String password = ctx.request().getFormAttribute("password");
		boolean keepLogin = Boolean.parseBoolean(ctx.request().getFormAttribute("keepLogin"));

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
