package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(name = "여행 모드", summary = "활성화된 여행 리스트 조회")
@RESTful(responseBody = "topic : String, title : String, (JSONArray)", successCode = 200, failureCode = 204)
@Route(uri = "/travel", method = HttpMethod.GET)
public class TravelList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet rs = DataBase.executeQuery("SELECT * FROM travels WHERE client_id=?", clientId);
		try {
			while(rs.next()) {
				JSONObject travelRoom = new JSONObject();
				travelRoom.put("topic", rs.getString("topic"));
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