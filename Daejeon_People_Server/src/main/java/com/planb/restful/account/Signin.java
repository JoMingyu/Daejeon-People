package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.OperationResult;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/account/signin", method = HttpMethod.POST)
public class Signin implements Handler<RoutingContext> {
	UserManager userManager;
	public Signin() {
		userManager = new UserManager();
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String password = ctx.request().getFormAttribute("password");
		
		OperationResult result = userManager.signin(id, password);
		if(result.isSuccess()) {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}