package com.planb.restful.travel;

import org.json.JSONObject;

import com.planb.support.chatting.ChatRoomManager;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드", summary = "여행 모드(채팅방) 생성 - 채팅방 참가 포함")
@REST(requestBody = "title : String", responseBody = "topic : String", successCode = 201)
@Route(uri = "/travel", method = HttpMethod.POST)
public class CreateTravel implements Handler<RoutingContext> {
	// 여행 개설
	
	@Override
	public void handle(RoutingContext ctx) {
		JSONObject response = new JSONObject();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String title = ctx.request().getFormAttribute("title");
		
		response.put("topic", ChatRoomManager.createRoom(clientId, title));
		
		ctx.response().setStatusCode(201);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
