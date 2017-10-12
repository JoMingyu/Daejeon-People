package com.planb.restful.travel.inside;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "여행 나가기")
@REST(requestBody = "topic : String", successCode = 200)
@Route(uri = "/travel/quit", method = HttpMethod.DELETE)
public class QuitTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		
		MySQL.executeUpdate("DELETE FROM travel_clients WHERE client_id=? AND topic=?", clientId, topic);
		
		ctx.response().setStatusCode(200).end();
		ctx.response().close();
	}
}
