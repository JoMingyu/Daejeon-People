package com.planb.restful.travel.inside;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행 모드 내부", summary = "여행(채팅방) 내부 정보 조회")
@REST(requestBody = "topic : String", responseBody = "id : String, phone_number : String, email : String, name : String, (JSONArray)", successCode = 200)
@Route(uri = "/travel/info", method = HttpMethod.GET)
public class TravelInfo implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getParam("topic");
		
		ctx.response().setStatusCode(200);
		ctx.response().end(getRoomInsideInfo(clientId, topic).toString());
		ctx.response().close();
	}
	
	private JSONArray getRoomInsideInfo(String requesterId, String topic) {
		JSONArray response = new JSONArray();
		ResultSet travelInfoSet = MySQL.executeQuery("SELECT * FROM travel_clients WHERE topic=?", topic);
		
		try {
			while(travelInfoSet.next()) {
				String clientId = travelInfoSet.getString("client_id");

				JSONObject clientInfo = UserManager.getUserInfo(clientId);
				response.put(clientInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
