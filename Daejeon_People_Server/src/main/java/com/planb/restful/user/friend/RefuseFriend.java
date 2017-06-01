package com.planb.restful.user.friend;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "친구", summary = "친구 요청 거절")
@REST(requestBody = "requester_id : String", successCode = 201)
@Route(uri = "/friend/refuse", method = HttpMethod.POST)
public class RefuseFriend implements Handler<RoutingContext> {
	// 친구요청 거절
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 친구 요청을 거절한 사람
		String requesterId = ctx.request().getFormAttribute("requester_id");
		// 친구 요청을 보낸 사람
		
		MySQL.executeUpdate("DELETE FROM friend_requests WHERE requesterId_id=? AND dst_id=?", requesterId, clientId);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
