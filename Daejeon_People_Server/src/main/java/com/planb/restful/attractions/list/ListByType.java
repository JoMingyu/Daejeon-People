package com.planb.restful.attractions.list;

import org.json.JSONArray;

import com.planb.support.restful.attractions.AttractionsListInquiry;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/attractions/list/:contenttypeid", method = HttpMethod.GET)
public class ListByType implements Handler<RoutingContext> {
	// 전체 여행지 조회

	@Override
	public void handle(RoutingContext ctx) {
		int contentTypeId = Integer.parseInt(ctx.request().getParam("contenttypeid"));
		
		JSONArray response = AttractionsListInquiry.inquire(ctx, contentTypeId);

		if (response == null || response.length() == 0) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
