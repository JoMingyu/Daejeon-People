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
		String msg = ctx.request().getFormAttribute("msg");
		
		MySQL.executeUpdate("DELETE FROM travel_invites WHERE dst_id=? AND topic=?", dst, topic);
		MySQL.executeUpdate("INSERT INTO travel_invites(src_id, dst_id, topic, msg) VALUES(?, ?, ?, ?, NOW())", clientId, dst, topic, msg);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
