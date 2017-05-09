package com.planb.restful.user.friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/friend", method = HttpMethod.GET)
public class FriendList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		JSONArray response = new JSONArray();
		
		String client = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet friendSet = database.executeQuery("SELECT friend_id FROM friend_list WHERE src_id='", client, "'");
		List<String> friendIdList = new ArrayList<String>();
		try {
			while(friendSet.next()) {
				friendIdList.add(friendSet.getString("friend_id"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(String friendId : friendIdList) {
			ResultSet friendInfoSet = database.executeQuery("SELECT * FROM account WHERE id='", friendId, "'");
			try {
				friendInfoSet.next();
				JSONObject friendInfo = new JSONObject();
				friendInfo.put("phone_number", friendInfoSet.getString("phone_number"));
				friendInfo.put("email", friendInfoSet.getString("email"));
				friendInfo.put("name", friendInfoSet.getString("name"));
				response.put(friendInfo);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(response.length() == 0) {
			// 아무 친구 요청도 없으면
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
