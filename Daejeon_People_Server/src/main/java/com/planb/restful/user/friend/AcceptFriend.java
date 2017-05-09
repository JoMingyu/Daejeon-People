package com.planb.restful.user.friend;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/friend/accept", method = HttpMethod.POST)
public class AcceptFriend implements Handler<RoutingContext> {
	// 친구요청 수락
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		
		String client = UserManager.getEncryptedIdFromSession(ctx);
		// 친구 요청을 수락한 사람
		String src = ctx.request().getFormAttribute("src");
		// 친구 요청을 보낸 사람
		
		database.executeUpdate("DELETE FROM friend_requests WHERE src_id='", src, "' AND dst_id='", client, "'");
		database.executeUpdate("INSERT INTO friend_list(src_id, friend_id) VALUES('", client, "', '", src, "')");
		// 친구 요청 수락에 대한 푸쉬 알림도 주자
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
