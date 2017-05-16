package com.planb.restful.account.after_signup;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/find/password/demand", method = HttpMethod.POST)
public class FindPassword_DemandCode implements Handler<RoutingContext> {
	UserManager userManager;
	
	public FindPassword_DemandCode() {
		userManager = new UserManager();
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String email = ctx.request().getFormAttribute("email");
		String name = ctx.request().getFormAttribute("name");
		
		if(userManager.findPasswordDemand(id, email, name)) {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}
