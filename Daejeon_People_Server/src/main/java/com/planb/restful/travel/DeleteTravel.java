package com.planb.restful.travel;

import com.planb.support.chatting.MySQL_Chat;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드", summary = "여행 삭제")
@REST(requestBody = "topic : String", successCode = 200)
@Route(uri = "/travel", method = HttpMethod.DELETE)
public class DeleteTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String topic = ctx.request().getParam("topic");
		
		MySQL.executeUpdate("DELETE * FROM travels WHERE topic=?", topic);
		MySQL_Chat.executeUpdate("DROP TABLE ?", topic);
		
		ctx.response().setStatusCode(200).end();
		ctx.response().close();
	}
}
