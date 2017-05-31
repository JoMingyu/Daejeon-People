package com.planb.restful.attractions.list;

import org.json.JSONArray;

import com.planb.support.restful.attractions.AttractionsListInquiry;
import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(functionCategory = "여행지 정보", summary = "카테고리 없는 여행지 리스트 조회")
@RESTful(params = "sort_type : int, page : int", responseBody = "content_id : int, title : String, wish : boolean, wish_count : int, address : String, category : String, image : String, mapx : double, mapy : double", successCode = 200, failureCode = 204)
@Route(uri = "/attractions/list/total", method = HttpMethod.GET)
public class TotalList implements Handler<RoutingContext> {
	// 전체 여행지 조회
	
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = AttractionsListInquiry.inquire(ctx);
		
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
