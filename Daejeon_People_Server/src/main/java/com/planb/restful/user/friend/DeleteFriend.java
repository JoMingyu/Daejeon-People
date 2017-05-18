package com.planb.restful.user.friend;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/friend/delete", method = HttpMethod.POST)
public class DeleteFriend implements Handler<RoutingContext> {
	/*
	 * 친구 삭제
	 * REST 아키텍처에 따라 DELETE /friend로 두어야 하지만 보안 상의 문제로 인해 POST로 결정
	 */
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 친구 삭제자
		String targetId = ctx.request().getFormAttribute("id");
		// 타겟
		
		database.executeUpdate("DELETE FROM friend_list WHERE client_id1='", clientId, "' AND client_id2='", targetId, "'");
		database.executeUpdate("DELETE FROM friend_list WHERE client_id2='", clientId, "' AND client_id1='", targetId, "'");
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
