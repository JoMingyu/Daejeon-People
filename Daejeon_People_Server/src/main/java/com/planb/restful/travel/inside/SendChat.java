package com.planb.restful.travel.inside;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "채팅 전송")
@REST()
@Route(uri = "/chat", method = HttpMethod.POST)
public class SendChat implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		String type = ctx.request().getFormAttribute("type");
		
		if(type == "text") {
			
		} else if(type == "attraction") {
			
		} else if(type == "image") {
			
		}
	}
}
