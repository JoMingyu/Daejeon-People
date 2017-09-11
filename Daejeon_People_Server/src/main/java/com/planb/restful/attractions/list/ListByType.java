package com.planb.restful.attractions.list;

import org.json.JSONArray;

import com.planb.support.restful.attractions.AttractionsListInquiry;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행지 정보", summary = "타입 지정된 여행지 리스트 조회")
@REST(params = "content_type_id : int, sort_type : int, page : int", responseBody = "content_id : int, title : String, wish : boolean, wish_count : int, address : String, category : String, image : String, mapx : double, mapy : double", successCode = 200, failureCode = 204)
@Route(uri = "/attractions/list", method = HttpMethod.GET)
public class ListByType implements Handler<RoutingContext> {
	// 카테고리로 나뉜 여행지 리스트 조회

	@Override
	public void handle(RoutingContext ctx) {
		int contentTypeId = Integer.parseInt(ctx.request().getParam("content_type_id"));

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
