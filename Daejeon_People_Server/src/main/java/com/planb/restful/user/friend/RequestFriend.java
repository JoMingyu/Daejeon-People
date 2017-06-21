package com.planb.restful.user.friend;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "친구", summary = "친구 요청")
@REST(requestBody = "dst : String", successCode = 201)
@Route(uri = "/friend/request", method = HttpMethod.POST)
public class RequestFriend implements Handler<RoutingContext> {
	// 친구 요청
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String dst = ctx.request().getFormAttribute("dst");
		
		MySQL.executeUpdate("INSERT INTO friend_requests VALUES(?, ?, CURDATE())", clientId, dst);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
