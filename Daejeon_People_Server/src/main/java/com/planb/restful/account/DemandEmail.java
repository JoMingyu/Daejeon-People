package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/signup/email/demand", method = HttpMethod.POST)
public class DemandEmail implements Handler<RoutingContext> {
	UserManager userManager;

	public DemandEmail() {
		userManager = new UserManager();
	}

	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		userManager.demandEmail(email);

		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
