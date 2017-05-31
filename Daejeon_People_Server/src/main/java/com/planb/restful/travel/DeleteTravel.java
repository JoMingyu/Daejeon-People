package com.planb.restful.travel;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(functionCategory = "여행 모드", summary = "여행 삭제")
@RESTful(requestBody = "topic : String", successCode = 200)
@Route(uri = "/travel", method = HttpMethod.DELETE)
public class DeleteTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String topic = ctx.request().getParam("topic");
		
		DataBase.executeUpdate("DELETE * FROM travels WHERE topic=?", topic);
		
		ctx.response().setStatusCode(200).end();
		ctx.response().close();
	}
}
