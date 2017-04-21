package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/signup", method = HttpMethod.POST)
public class Signup implements Handler<RoutingContext> {
	UserManager userManager;

	public Signup() {
		userManager = new UserManager();
	}

	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String password = ctx.request().getFormAttribute("password");
		String email = ctx.request().getFormAttribute("email");
		String name = ctx.request().getFormAttribute("name");

		userManager.signup(id, password, email, name);

		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
