package com.planb.restful.user.friend;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/friend/refuse", method = HttpMethod.POST)
public class RefuseFriend implements Handler<RoutingContext> {
	// 친구요청 거절
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 친구 요청을 거절한 사람
		String src = ctx.request().getFormAttribute("src");
		// 친구 요청을 보낸 사람
		
		DataBase.executeUpdate("DELETE FROM friend_requests WHERE src_id=? AND dst_id=?", src, clientId);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
