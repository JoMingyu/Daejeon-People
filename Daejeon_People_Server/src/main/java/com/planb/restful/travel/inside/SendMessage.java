package com.planb.restful.travel.inside;

import com.planb.support.chatting.ChatInsideManager;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "메시지 전송")
@REST(requestBody = "topic : String, type : String, (text or image), content : String(type is text)", successCode = 201)
@Route(uri = "/chat", method = HttpMethod.POST)
public class SendMessage implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		String type = ctx.request().getFormAttribute("type");
		
		if(type.equals("text")) {
			ChatInsideManager.sendTextMessage(clientId, topic, ctx.request().getFormAttribute("content"));
		} else if(type.equals("image")) {
			ChatInsideManager.sendImage(clientId, topic, ctx.fileUploads());
		}
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
