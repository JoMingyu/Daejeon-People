package com.planb.restful.attractions.list;

import org.json.JSONArray;

import com.planb.support.restful.attractions.AttractionsListInquiry;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/attractions/list/cultural_facility", method = HttpMethod.GET)
public class CulturalFacilityList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = AttractionsListInquiry.inquire(ctx, 14);
		
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
