package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.UserAccountManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/find/password", method = HttpMethod.POST)
public class FindPassword implements Handler<RoutingContext> {
	UserAccountManager userManager;
	
	public FindPassword() {
		userManager = new UserAccountManager();
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String email = ctx.request().getFormAttribute("email");
		String name = ctx.request().getFormAttribute("name");
		
		boolean result = userManager.findPassword(id, email, name);
		if(result) {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}
