package com.planb.restful.travel.inside;

import org.json.JSONArray;

import com.planb.support.chatting.ChatRoomManager;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "읽지 않은 채팅 기록 조회")
@REST(requestBody = "topic : String, idx : int", responseBody = "idx : int, type : String, name : String, content : String, (JSONArray)", successCode = 200, failureCode = 204)
@Route(uri = "/chat/read", method = HttpMethod.POST)
public class GetUnreadMessages implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String topic = ctx.request().getFormAttribute("topic");
		int startIndex = Integer.parseInt(ctx.request().getFormAttribute("idx"));
		
		JSONArray messages = ChatRoomManager.getUnreadMessages(topic, startIndex);
		
		if(messages.length() == 0) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(messages.toString());
			ctx.response().close();
		}
	}
}
