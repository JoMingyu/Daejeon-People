package com.planb.restful.user.friend;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "친구", summary = "친구 삭제")
@REST(requestBody = "id : String", successCode = 201)
@Route(uri = "/friend/delete", method = HttpMethod.DELETE)
public class DeleteFriend implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 친구 삭제자
		String targetId = ctx.request().getFormAttribute("id");
		// 타겟
		
		MySQL.executeUpdate("DELETE FROM friend_list WHERE client_id1=? AND client_id2=?", clientId, targetId);
		MySQL.executeUpdate("DELETE FROM friend_list WHERE client_id2=? AND client_id1=?", clientId, targetId);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
