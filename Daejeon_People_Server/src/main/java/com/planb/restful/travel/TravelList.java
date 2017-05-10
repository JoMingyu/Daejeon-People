package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel", method = HttpMethod.GET)
public class TravelList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet rs = database.executeQuery("SELECT * FROM travels WHERE client_id='", clientId, "'");
		try {
			while(rs.next()) {
				JSONObject travelRoom = new JSONObject();
				travelRoom.put("notification_key_name", rs.getString("notification_key_name"));
				travelRoom.put("title", rs.getString("title"));
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