package com.planb.restful.travel.inside;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "여행에 유저 초대")
@REST(requestBody = "dst : String, topic : String, msg : String", successCode = 201)
@Route(uri = "/travel/invite", method = HttpMethod.POST)
public class InviteTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String dst = ctx.request().getFormAttribute("dst");
		String topic = ctx.request().getFormAttribute("topic");
		
		MySQL.executeUpdate("DELETE FROM travel_clients WHERE topic=? AND client_id=?", topic, dst);
		MySQL.executeUpdate("INSERT INTO travel_clients VALUES(?, ?)", topic, clientId);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
