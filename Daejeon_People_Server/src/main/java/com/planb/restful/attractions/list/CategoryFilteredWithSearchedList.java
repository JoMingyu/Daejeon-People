package com.planb.restful.attractions.list;

import org.json.JSONArray;

import com.planb.support.restful.attractions.AttractionsListInquiry;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행지 정보", summary = "카테고리(분류) 지정되고 검색 포함된 여행지 조회")
@REST(params = "category: String, sort_type: int, page: int, keyword: String", responseBody = "content_id : int, title : String, wish : boolean, wish_count : int, address : String, category : String, image : String, mapx : double, mapy : double", successCode = 200, failureCode = 204)
@Route(uri = "/attractions/list/category/keyworded", method = HttpMethod.GET)
public class CategoryFilteredWithSearchedList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String category = ctx.request().getParam("category");
		String keyword = ctx.request().getParam("keyword");

		JSONArray response = AttractionsListInquiry.inquireBasedKeyword(ctx, category, keyword);

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
