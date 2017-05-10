package com.planb.restful.user.friend;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/friend/request", method = HttpMethod.POST)
public class RequestFriend implements Handler<RoutingContext> {
	// 친구 요청
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String dst = ctx.request().getFormAttribute("dst");
		
		database.executeUpdate("INSERT INTO friend_requests(src_id, dst_id, date) VALUES('", clientId, "', '", dst, "', CURDATE())");
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}