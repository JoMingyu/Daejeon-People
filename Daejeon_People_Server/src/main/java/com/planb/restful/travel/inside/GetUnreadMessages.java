package com.planb.restful.travel.inside;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.chatting.ChatManager;
import com.planb.support.chatting.MySQL_Chat;
import com.planb.support.routing.API;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "읽지 않은 채팅 기록 조회")
@Route(uri = "/chat/read", method = HttpMethod.POST)
public class GetUnreadMessages implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String topic = ctx.request().getFormAttribute("topic");
		int startIdx = Integer.parseInt(ctx.request().getFormAttribute("idx"));
		
		ResultSet chatLogSet = MySQL_Chat.executeQuery("SELECT * FROM " + topic + " WHERE idx BETWEEN ? AND ?", startIdx, ChatManager.getLastIndexInRoom(topic));
		
		JSONArray response = new JSONArray();
		try {
			while(chatLogSet.next()) {
				JSONObject msg = new JSONObject();
				
				msg.put("idx", chatLogSet.getInt("idx"));
				msg.put("type", chatLogSet.getString("type"));
				msg.put("name", chatLogSet.getString("name"));
				if(chatLogSet.getString("content") != null) {
					msg.put("content", chatLogSet.getString("content"));
				}
				
				MySQL_Chat.executeUpdate("UPDATE " + topic + " SET remaining_views=? WHERE idx=?", chatLogSet.getInt("remaining_views") - 1, chatLogSet.getInt("idx"));
				
				response.put("chatLogSet");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(response.length() == 0) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
