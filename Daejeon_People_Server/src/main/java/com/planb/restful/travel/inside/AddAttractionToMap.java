package com.planb.restful.travel.inside;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.chatting.ChatManager;
import com.planb.support.chatting.MySQL_Chat;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "지도에 여행지 추가")
@REST(requestBody = "topic : String, content_id : int", successCode = 201)
@Route(uri = "/map", method = HttpMethod.POST)
public class AddAttractionToMap implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		int contentId = Integer.parseInt(ctx.request().getFormAttribute("content_id"));

		ResultSet travelPinSet = MySQL.executeQuery("SELECT * FROM travel_pins WHERE topic=? AND content_id=?", topic, contentId);
		try {
			if(travelPinSet.next()) {
				ctx.response().setStatusCode(204).end();
				ctx.response().close();
				return;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet clientSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		ResultSet attractionInfoSet = MySQL.executeQuery("SELECT * FROM attractions_basic WHERE content_id=?", contentId);
		try {
			clientSet.next();
			attractionInfoSet.next();
			
			String title = attractionInfoSet.getString("title");
			String owner = clientSet.getString("name");
			double mapX = attractionInfoSet.getDouble("mapx");
			double mapY = attractionInfoSet.getDouble("mapy");
			
			MySQL.executeUpdate("INSERT INTO travel_pins VALUES(?, ?, ?, ?, ?, ?)", topic, contentId, title, owner, mapX, mapY);
			MySQL_Chat.executeUpdate("INSERT INTO ?(remaning_views, type, name, content) VALUES(?, ?, ?, ?)", topic, ChatManager.getUserCountInRoom(topic), "add_att", owner, title);
			
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
