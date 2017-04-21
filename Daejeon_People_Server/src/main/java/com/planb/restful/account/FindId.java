package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/find/id", method = HttpMethod.POST)
public class FindId implements Handler<RoutingContext> {
	UserManager userManager;
	
	public FindId() {
		userManager = new UserManager();
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		String name = ctx.request().getFormAttribute("name");
		
		boolean result = userManager.findId(email, name);
		if(result) {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}
