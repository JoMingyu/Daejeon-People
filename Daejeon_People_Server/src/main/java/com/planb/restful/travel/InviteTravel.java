package com.planb.restful.travel;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel/invite", method = HttpMethod.POST)
public class InviteTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String dst = ctx.request().getFormAttribute("dst");
		String topic = ctx.request().getFormAttribute("topic");
		String msg = ctx.request().getFormAttribute("msg");
		
		DataBase.executeUpdate("DELETE FROM travel_invites WHERE dst_id='", dst, "' AND topic='", "'");
		DataBase.executeUpdate("INSERT INTO travel_invites VALUES('", clientId, "', '", dst, "', '", topic, "', '", msg, "', NOW())");
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
