package com.planb.restful.travel;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드", summary = "여행 초대 거절")
@REST(requestBody = "topic : String", successCode = 201)
@Route(uri = "/travel/refuse", method = HttpMethod.POST)
public class RefuseTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 여행 초대를 거절한 사람
		String topic = ctx.request().getFormAttribute("topic");
		
		MySQL.executeUpdate("DELETE FROM travel_invites WHERE dst_id=? AND topic=?", clientId, topic);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
