package com.planb.restful.travel.inside;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.chatting.ChatRoomManager;
import com.planb.support.chatting.MySQL_Chat;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "읽지 않은 채팅 기록 조회")
@REST(requestBody = "topic : String, idx : int", responseBody = "idx : int, type : String, name : String, content : String, remaining_views : int, (JSONArray)", successCode = 201, failureCode = 204)
@Route(uri = "/chat/read", method = HttpMethod.POST)
public class GetUnreadMessages implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String topic = ctx.request().getFormAttribute("topic");
		int startIndex = Integer.parseInt(ctx.request().getFormAttribute("idx"));
		
		JSONArray messages = getUnreadMessages(topic, startIndex);
		
		if(messages.length() == 0) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(201);
			ctx.response().end(messages.toString());
			ctx.response().close();
		}
	}
	
	private JSONArray getUnreadMessages(String topic, int startIndex) {
		ResultSet chatLogSet = MySQL_Chat.executeQuery("SELECT * FROM " + topic + " WHERE idx BETWEEN ? AND ?", startIndex, ChatRoomManager.getLastIndexInRoom(topic));
		
		JSONArray messages = new JSONArray();
		try {
			while(chatLogSet != null ? chatLogSet.next() : false) {
				JSONObject msg = new JSONObject();
				
				msg.put("idx", chatLogSet.getInt("idx"));
				msg.put("type", chatLogSet.getString("type"));
				msg.put("name", chatLogSet.getString("name"));
				if(chatLogSet.getString("content") != null) {
					msg.put("content", chatLogSet.getString("content"));
				}
				msg.put("remaining_views", chatLogSet.getInt("remaining_views") - 1);
				
				MySQL_Chat.executeUpdate("UPDATE " + topic + " SET remaining_views=? WHERE idx=?", chatLogSet.getInt("remaining_views") - 1, chatLogSet.getInt("idx"));
				
				messages.put(msg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return messages;
	}
}
