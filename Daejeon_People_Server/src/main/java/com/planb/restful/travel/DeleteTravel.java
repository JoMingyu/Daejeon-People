package com.planb.restful.travel;

import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel", method = HttpMethod.DELETE)
public class DeleteTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String topic = ctx.request().getParam("topic");
	}
}
