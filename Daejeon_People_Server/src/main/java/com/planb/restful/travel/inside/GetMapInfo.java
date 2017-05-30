package com.planb.restful.travel.inside;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.routing.Route;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/map", method = HttpMethod.GET)
public class GetMapInfo implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		
		String topic = ctx.request().getFormAttribute("topic");
		
		ResultSet travelPinSet = DataBase.executeQuery("SELECT * FROM travel_pins WHERE topic=?", topic);
		try {
			while(travelPinSet.next()) {
				JSONObject pinInfo = new JSONObject();
				
				pinInfo.put("content_id", travelPinSet.getInt("content_id"));
				pinInfo.put("title", travelPinSet.getString("title"));
				pinInfo.put("owner", travelPinSet.getString("owner"));
				pinInfo.put("mapx", travelPinSet.getDouble("mapx"));
				pinInfo.put("mapy", travelPinSet.getDouble("mapy"));
				
				response.put(pinInfo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(response.length() == 0) {
			// 아무 핀도 없으면
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
