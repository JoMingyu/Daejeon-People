package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
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

@API(functionCategory = "여행 모드", summary = "활성화된 여행 리스트 조회")
@REST(responseBody = "topic : String, title : String, idx : int(최종 메시지 인덱스), (JSONArray)", successCode = 200, failureCode = 204)
@Route(uri = "/travel", method = HttpMethod.GET)
public class TravelList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM travels WHERE client_id=?", clientId);
		try {
			while(rs.next()) {
				JSONObject travelRoom = new JSONObject();
				travelRoom.put("topic", rs.getString("topic"));
				travelRoom.put("title", rs.getString("title"));
				
				ResultSet chat = MySQL_Chat.executeQuery("SELECT idx FROM " + rs.getString("topic") + " ORDER BY idx DESC limit 1");
				if(chat.next()) {
					travelRoom.put("idx", chat.getInt("idx"));
				} else {
					travelRoom.put("idx", 0);
				}
				
				response.put(travelRoom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(response.length() == 0) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}