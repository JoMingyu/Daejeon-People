package com.planb.restful.attractions.list;

import org.json.JSONArray;

import com.planb.support.restful.attractions.AttractionsListInquiry;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/attractions/list/accommodation", method = HttpMethod.GET)
public class AccommodationList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = AttractionsListInquiry.inquire(ctx, 32);
		
		if(response == null || response.length() == 0) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
