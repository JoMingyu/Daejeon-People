package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel/info", method = HttpMethod.GET)
public class TravelInfo implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		AES256 aes = UserManager.getAES256Instance();
		
		String topic = ctx.request().getParam("topic");
		
		ResultSet info = DataBase.executeQuery("SELECT * FROM travels WHERE topic='", topic, "'");
		List<String> clientIdList = new ArrayList<String>();
		try {
			while(info.next()) {
				clientIdList.add(info.getString("client_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		for(String clientId : clientIdList) {
			ResultSet clientInfo = DataBase.executeQuery("SELECT * FROM account WHERE id='", clientId, "'");
			JSONObject client = new JSONObject();
			try {
				clientInfo.next();
				client.put("id", clientId);
				client.put("name", aes.decrypt(clientInfo.getString("name")));
				client.put("email", aes.decrypt(clientInfo.getString("email")));
				response.put(client);
			} catch (JSONException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
