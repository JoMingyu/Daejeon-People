package com.planb.restful.user.common;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "사용자", summary = "내 정보")
@REST(responseBody = "id : String, email : String, phone_number : String, name : String, friend_req_count : int, travel_req_count : int", successCode = 200)
@Route(uri = "/user", method = HttpMethod.GET)
public class MyInfo implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);

		JSONObject response = UserManager.getUserInfo(clientId);
		
		ResultSet rs;
		try {
			rs = MySQL.executeQuery("SELECT COUNT(*) FROM friend_requests WHERE dst_id=?", clientId);
			if(rs != null && rs.next()) {
				response.put("friend_req_count", rs.getInt(1));
			} else {
				response.put("friend_req_count", 0);
			}

			rs = MySQL.executeQuery("SELECT COUNT(*) FROM travel_invites WHERE dst_id=?", clientId);
			if(rs != null && rs.next()) {
				response.put("travel_req_count", rs.getInt(1));
			} else {
				response.put("travel_req_count", 0);
			}
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
