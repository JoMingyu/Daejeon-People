package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.UserAccountManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/signup/email/check", method = HttpMethod.POST)
public class CheckEmailExist implements Handler<RoutingContext> {
	UserAccountManager userManager;

	public CheckEmailExist() {
		userManager = new UserAccountManager();
	}

	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		System.out.println(email);
		
		if (userManager.checkEmailExists(email)) {
			ctx.response().setStatusCode(409).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		}
	}
}
