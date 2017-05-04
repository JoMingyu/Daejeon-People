package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.UserAccountManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/logout", method = HttpMethod.POST)
public class Logout implements Handler<RoutingContext> {
	UserAccountManager userManager;
	
	public Logout() {
		userManager = new UserAccountManager();
	}
	

	@Override
	public void handle(RoutingContext ctx) {
		userManager.logout(ctx);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
