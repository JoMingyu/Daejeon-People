package com.planb.restful.attractions.list;

import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/attractions/list/tourrism", method = HttpMethod.GET)
public class TourrismList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		int sortType = Integer.parseInt(ctx.request().getParam("sort_type"));
		int page = Integer.parseInt(ctx.request().getParam("page"));
		
		switch(sortType) {
		// sort type 협의 필요함
		default:
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
			break;
		}
	}
}
