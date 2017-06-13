package com.planb.restful.user.common;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "사용자", summary = "ID 기반 사용자 정보 조회")
@REST(requestBody = "id : String", responseBody = "id : String, phone_number : String, email : String, name : String", successCode = 201)
@Route(uri = "/user", method = HttpMethod.POST)
public class AnotherUser implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		
		ctx.response().setStatusCode(201);
		ctx.response().end(UserManager.getUserInfo(id).toString());
		ctx.response().close();
	}
}
