package com.planb.restful.travel;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.json.JSONObject;

import com.planb.support.chatting.MySQL_Chat;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

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
		
		response.put("topic", createRoom(clientId, title));
		
		ctx.response().setStatusCode(201);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
	
	private String createRoom(String clientId, String title) {
		String topic;
		while(true) {
			topic = UUID.randomUUID().toString().replaceAll("-", "");
			ResultSet rs = MySQL.executeQuery("SELECT * FROM travel_list WHERE topic=?", topic);
			try {
				if(!(rs != null && rs.next())) {
					MySQL.executeUpdate("INSERT INTO travel_list VALUES(?, ?)", topic, title);
					MySQL.executeUpdate("INSERT INTO travel_clients VALUES(?, ?)", topic, clientId);
					MySQL_Chat.executeUpdate("CREATE TABLE " + topic + "(idx INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT, remaining_views INT(3) NOT NULL, type VARCHAR(20) NOT NULL, name VARCHAR(256), content VARCHAR(1024))");
					new File("chatting_resources/" + topic).mkdirs();
					return topic;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
