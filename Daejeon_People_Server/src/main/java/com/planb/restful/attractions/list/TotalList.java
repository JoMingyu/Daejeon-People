package com.planb.restful.attractions.list;

import org.json.JSONArray;

import com.planb.support.attractions.AttractionsListInquiry;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/attractions/list/total", method = HttpMethod.GET)
public class TotalList implements Handler<RoutingContext> {
	// 전체 여행지 조회
	
	@Override
	public void handle(RoutingContext ctx) {
		int sortType = Integer.parseInt(ctx.request().getParam("sort_type"));
		int page = Integer.parseInt(ctx.request().getParam("page"));
		
		JSONArray response = AttractionsListInquiry.getTotalDatas(ctx);
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
