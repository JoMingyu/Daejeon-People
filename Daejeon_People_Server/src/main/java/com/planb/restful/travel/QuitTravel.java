package com.planb.restful.travel;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel", method = HttpMethod.DELETE)
public class QuitTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		
		DataBase.executeUpdate("DELETE FROM travels WHERE client_id=? AND topic=?", clientId, topic);
		
		ctx.response().setStatusCode(200).end();
		ctx.response().close();
	}
}
