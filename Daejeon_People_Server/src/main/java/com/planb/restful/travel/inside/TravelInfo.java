package com.planb.restful.travel.inside;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.Route;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel/info", method = HttpMethod.GET)
public class TravelInfo implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		
		String topic = ctx.request().getParam("topic");
		
		ResultSet travelInfoSet = DataBase.executeQuery("SELECT * FROM travels WHERE topic=?", topic);
		try {
			while(travelInfoSet.next()) {
				String clientId = travelInfoSet.getString("client_id");
				ResultSet clientInfoSet = DataBase.executeQuery("SELECT * FROM account WHERE id=?", clientId);
				clientInfoSet.next();

				JSONObject clientInfo = new JSONObject();
				clientInfo.put("id", clientId);
				clientInfo.put("name", AES256.decrypt(clientInfoSet.getString("name")));
				clientInfo.put("email", AES256.decrypt(clientInfoSet.getString("email")));
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
