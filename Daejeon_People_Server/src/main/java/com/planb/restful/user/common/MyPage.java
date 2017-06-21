package com.planb.restful.user.common;

import org.json.JSONObject;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "사용자", summary = "마이페이지")
@REST(responseBody = "email : String, phone_number : String, name : String", successCode = 200, failureCode = 204)
@Route(uri = "/mypage", method = HttpMethod.GET)
public class MyPage implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		if(UserManager.isLogined(ctx)) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
			return;
		}

		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		JSONObject response = UserManager.getUserInfo(clientId);
		response.remove("id");
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
