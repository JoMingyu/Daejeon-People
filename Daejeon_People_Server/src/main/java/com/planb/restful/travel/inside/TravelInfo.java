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

@API(functionCategory = "여행 모드 내부", summary = "여행(채팅방) 정보 조회")
@REST(requestBody = "topic : String", responseBody = "id : String, phone_number : String, email : String, name : String", successCode = 200)
@Route(uri = "/travel/info", method = HttpMethod.GET)
public class TravelInfo implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		
		String requesterId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getParam("topic");
		
		ResultSet travelInfoSet = MySQL.executeQuery("SELECT * FROM travels WHERE topic=?", topic);
		try {
			while(travelInfoSet.next()) {
				String clientId = travelInfoSet.getString("client_id");
				ResultSet clientInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
				clientInfoSet.next();

				JSONObject clientInfo = new JSONObject();
				clientInfo.put("id", clientId);
				clientInfo.put("phone_number", clientInfoSet.getString("phone_number") == null ? "전화번호 없음" : AES256.decrypt(clientInfoSet.getString("phone_number")));
				clientInfo.put("email", AES256.decrypt(clientInfoSet.getString("email")));
				clientInfo.put("name", requesterId == clientId ? "나" : AES256.decrypt(clientInfoSet.getString("name")));
				response.put(clientInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
