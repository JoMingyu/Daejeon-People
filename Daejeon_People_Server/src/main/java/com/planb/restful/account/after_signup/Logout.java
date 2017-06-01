package com.planb.restful.account.after_signup;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "계정", summary = "로그아웃")
@REST(successCode = 201)
@Route(uri = "/logout", method = HttpMethod.POST)
public class Logout implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		UserManager userManager = new UserManager();
		userManager.logout(ctx);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
