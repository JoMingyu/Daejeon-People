package com.planb.restful.user.friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.AES256;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/friend", method = HttpMethod.GET)
public class FriendList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		AES256 aes = UserManager.getAES256Instance();
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet friendSet = database.executeQuery("SELECT * FROM friend_list WHERE client_id1='", clientId, "' OR client_id2='", clientId, "'");
		List<String> friendIdList = new ArrayList<String>();
		try {
			while(friendSet.next()) {
				if(friendSet.getString("client_id1") != clientId) {
					friendIdList.add(friendSet.getString("client_id1"));
				} else {
					friendIdList.add(friendSet.getString("client_id2"));
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(String friendId : friendIdList) {
			ResultSet friendInfoSet = database.executeQuery("SELECT * FROM account WHERE id='", friendId, "'");
			try {
				friendInfoSet.next();
				JSONObject friend = new JSONObject();
				friend.put("friend_id", friendId);
				friend.put("phone_number", aes.decrypt(friendInfoSet.getString("phone_number")));
				friend.put("email", aes.decrypt(friendInfoSet.getString("email")));
				friend.put("name", aes.decrypt(friendInfoSet.getString("name")));
				response.put(friend);
			} catch(SQLException e) {
				e.printStackTrace();
			}
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
