package com.planb.restful.test;

import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/test", method = HttpMethod.POST)
public class Test implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		ctx.response().end("한글 응답");
		ctx.response().close();
	}
}
