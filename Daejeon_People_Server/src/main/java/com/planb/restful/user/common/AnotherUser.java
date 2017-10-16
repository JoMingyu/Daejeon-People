package com.planb.restful.user.common;

import org.json.JSONObject;

import com.planb.support.crypto.AES256;
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
		String id = AES256.encrypt(ctx.request().getFormAttribute("id"));
		
		JSONObject userInfo = UserManager.getUserInfo(id);
		if (userInfo == null) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(201);
			ctx.response().end(userInfo.toString());
			ctx.response().close();
		}
	}
}
