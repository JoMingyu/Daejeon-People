package com.planb.restful.user.friend;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "친구", summary = "친구 목록")
@REST(responseBody = "id : String, phone_number : String, email : String, name : String, (JSONArray)", successCode = 200, failureCode = 204)
@Route(uri = "/friend", method = HttpMethod.GET)
public class FriendList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet friendSet = MySQL.executeQuery("SELECT * FROM friend_list WHERE client_id1=? OR client_id2=?", clientId, clientId);
		try {
			while(friendSet != null ? friendSet.next() : false) {
				String friendId;
				
				if(!friendSet.getString("client_id1").equals(clientId)) {
					friendId = friendSet.getString("client_id1");
				} else {
					friendId = friendSet.getString("client_id2");
				}

				response.put(UserManager.getUserInfo(friendId));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(response.length() == 0) {
			// 아무 친구도 없으면
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
