package com.planb.restful.attractions.list;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.restful.attractions.AttractionsListInquiry;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행지 정보", summary = "카테고리(분류) 지정된 여행지 리스트 조회")
@REST(params = "category: String, sort_type : int, page : int", responseBody = "content_id : int, title : String, wish : boolean, wish_count : int, address : String, category : String, image : String, mapx : double, mapy : double", successCode = 200, failureCode = 204)
@Route(uri = "/attractions/list/category", method = HttpMethod.GET)
public class CategoryFilteredList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String category = ctx.request().getParam("category");

		JSONArray response = AttractionsListInquiry.inquire(ctx, category);
		JSONObject temp = new JSONObject();
		temp.put("data", response);
		
		if (response == null || response.length() == 0) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(temp.toString());
			ctx.response().close();
		}
	}
}