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
		
		ResultSet travelAttractionSet = DataBase.executeQuery("SELECT * FROM travel_attractions WHERE topic=?", topic);
		try {
			while(travelAttractionSet.next()) {
				JSONObject attractionInfo = new JSONObject();
				
				attractionInfo.put("content_id", travelAttractionSet.getInt("content_id"));
				attractionInfo.put("title", travelAttractionSet.getString("title"));
				attractionInfo.put("owner", travelAttractionSet.getString("owner"));
				attractionInfo.put("mapx", travelAttractionSet.getDouble("mapx"));
				attractionInfo.put("mapy", travelAttractionSet.getDouble("mapy"));
				
				response.put(attractionInfo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
