package com.planb.restful.user.friend;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "친구", summary = "친구 요청 수락")
@REST(requestBody = "requester_id : String", successCode = 201)
@Route(uri = "/friend/accept", method = HttpMethod.POST)
public class AcceptFriend implements Handler<RoutingContext> {
	// 친구요청 수락
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 친구 요청을 수락한 사람
		String requesterId = ctx.request().getFormAttribute("requester_id");
		// 친구 요청을 보낸 사람
		
		MySQL.executeUpdate("DELETE FROM friend_requests WHERE requesterId_id=? AND dst_id=?", requesterId, clientId);
		MySQL.executeUpdate("INSERT INTO friend_list VALUES(?, ?)", clientId, requesterId);
		// 친구 요청 수락에 대한 푸쉬 알림도 주자
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
