package com.planb.restful.travel.inside;

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

@API(functionCategory = "여행 모드 내부", summary = "지도에 여행지 추가")
@REST(requestBody = "topic : String, content_id : int", successCode = 201, failureCode = 204, etc = "이미 추가된 여행지일 경우 fail")
@Route(uri = "/map", method = HttpMethod.POST)
public class AddAttractionToMap implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		int contentId = Integer.parseInt(ctx.request().getFormAttribute("content_id"));
		
		ctx.response().setStatusCode(addAttraction(clientId, topic, contentId)).end();
		ctx.response().close();
	}
	
	private int addAttraction(String clientId, String topic, int contentId) {
		ResultSet travelPinSet = MySQL.executeQuery("SELECT * FROM travel_pins WHERE topic=? AND content_id=?", topic, contentId);
		try {
			if(travelPinSet != null ? travelPinSet.next() : false) {
				// 이미 공유된 여행지
				return 204;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		ResultSet attractionInfoSet = MySQL.executeQuery("SELECT * FROM attractions_basic WHERE content_id=?", contentId);
		try {
			assert userInfoSet != null;
			userInfoSet.next();
			assert attractionInfoSet != null;
			attractionInfoSet.next();
			
			String title = attractionInfoSet.getString("title");
			String owner = userInfoSet.getString("name");
			double mapX = attractionInfoSet.getDouble("mapx");
			double mapY = attractionInfoSet.getDouble("mapy");
			
			MySQL.executeUpdate("INSERT INTO travel_pins VALUES(?, ?, ?, ?, ?, ?)", topic, contentId, title, owner, mapX, mapY);
			MySQL_Chat.executeUpdate("INSERT INTO" + topic + "(remaning_views, type, name, content) VALUES(?, ?, ?, ?)", ChatRoomManager.getUserCountInRoom(topic), "add_att", owner, title);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 201;
	}
}
