package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.chatting.ChatRoomManager;
import com.planb.support.chatting.MySQL_Chat;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드", summary = "여행 초대 수락")
@REST(requestBody = "topic : String", successCode = 201)
@Route(uri = "/travel/accept", method = HttpMethod.POST)
public class AcceptTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		
		enterRoom(clientId, topic);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
	
	private void enterRoom(String clientId, String topic) {
		MySQL.executeUpdate("DELETE FROM travel_invites WHERE dst_id=? AND topic=?", clientId, topic);
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM travels WHERE topic=?", topic);
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		try {
			rs.next();
			userInfoSet.next();
			
			MySQL.executeUpdate("INSERT INTO travels VALUES(?, ?, ?)", topic, rs.getString("title"), clientId);
			MySQL_Chat.executeUpdate("INSERT INTO " + topic +"(remaining_views, type, name) VALUES(?, ?, ?)", ChatRoomManager.getUserCountInRoom(topic), "enter", userInfoSet.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
