package com.planb.restful.user.friend;

import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/friend/list", method = HttpMethod.GET)
public class FriendList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		
	}
}
