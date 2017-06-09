package com.planb.restful.test;

import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/test", method = HttpMethod.POST)
public class Test implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		System.out.println(ctx.request().getParam("test_p"));
		System.out.println(ctx.request().getFormAttribute("test_f"));
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
