package com.planb.restful.travel.inside;

import java.sql.ResultSet;

import com.planb.support.chatting.ChatManager;
import com.planb.support.chatting.MySQL_Chat;
import com.planb.support.routing.API;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "읽지 않은 채팅 기록 조회")
@Route(uri = "/chat/read", method = HttpMethod.POST)
public class GetUnreadMessages implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		int startIdx = Integer.parseInt(ctx.request().getFormAttribute("idx"));
		
		int userCount = ChatManager.getUserCountInRoom(topic);
		ResultSet chatLogSet = MySQL_Chat.executeQuery("SELECT * FROM " + topic + " WHERE idx BETWEEN", startIdx);
	}
}
