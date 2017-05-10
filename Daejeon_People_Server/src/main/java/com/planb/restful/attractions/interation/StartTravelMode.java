package com.planb.restful.attractions.interation;

import java.util.UUID;

import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel/start", method = HttpMethod.POST)
public class StartTravelMode implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		UUID uuid = UUID.randomUUID();
		String topic = uuid.toString();
	}
}
