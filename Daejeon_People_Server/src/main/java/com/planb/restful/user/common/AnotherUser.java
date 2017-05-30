package com.planb.restful.user.common;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/user", method = HttpMethod.POST)
public class AnotherUser implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		
		ctx.response().setStatusCode(201);
		ctx.response().end(UserManager.getUserInfo(id).toString());
		ctx.response().close();
	}
}
