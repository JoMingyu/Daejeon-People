package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.chatting.ChatMessageSaver;
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
		// 여행 초대를 수락한 사람
		String topic = ctx.request().getFormAttribute("topic");
		
		MySQL.executeUpdate("DELETE FROM travel_invites WHERE dst_id=? AND topic=?", clientId, topic);
		ResultSet rs = MySQL.executeQuery("SELECT * FROM travels WHERE topic=?", topic);
		try {
			rs.next();
			String title = rs.getString("title");
			MySQL.executeUpdate("INSERT INTO travels VALUES(?, ?, ?)", topic, title, clientId);
			new ChatMessageSaver(topic).userEntered(clientId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
