package com.planb.restful.travel;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel/refuse", method = HttpMethod.POST)
public class RefuseTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 여행 초대를 거절한 사람
		String notificationKeyName = ctx.request().getFormAttribute("notification_key_name");
		database.executeUpdate("DELETE FROM travel_invites WHERE dst_id='", clientId, "' AND notification_key_name='", notificationKeyName, "'");
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
