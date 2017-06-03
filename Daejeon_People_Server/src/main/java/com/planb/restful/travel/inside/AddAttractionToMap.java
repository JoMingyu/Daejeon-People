package com.planb.restful.travel.inside;

import com.planb.support.chatting.ChatInsideManager;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "지도에 여행지 추가")
@REST(requestBody = "topic : String, content_id : int", successCode = 201, failureCode = 204, etc = "이미 추가된 여행지일 경우 fail")
@Route(uri = "/map", method = HttpMethod.POST)
public class AddAttractionToMap implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		int contentId = Integer.parseInt(ctx.request().getFormAttribute("content_id"));
		
		ctx.response().setStatusCode(ChatInsideManager.addAttraction(clientId, topic, contentId)).end();
		ctx.response().close();
	}
}
