package com.planb.restful.attractions.list;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행지 정보", summary = "여행지 검색")
@REST(params = "keyword: String", responseBody = "content_id : int, title : String, wish : boolean, wish_count : int, address : String, category : String, image : String, mapx : double, mapy : double", successCode = 200, failureCode = 204)
@Route(uri = "/attractions/list/keyworded", method = HttpMethod.GET)
public class SearchedList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String keyword = ctx.request().getParam("keyword");
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM attractions_basic WHERE title LIKE '%" + keyword + "%'");
		JSONArray response = new JSONArray();
		try {
			while(rs.next()) {
				JSONObject attraction = new JSONObject();
				
				attraction.put("wish_count", rs.getInt("wish_count"));
				attraction.put("content_id", rs.getInt("content_id"));
				attraction.put("title", rs.getString("title"));
				attraction.put("address", rs.getString("address"));
				attraction.put("category", rs.getString("cat3"));
				attraction.put("image", rs.getString("image_big_url"));
				attraction.put("mapx", rs.getDouble("mapx"));
				attraction.put("mapy", rs.getDouble("mapy"));
				
				response.put(attraction);
			}
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		}
		
		if(response.length() == 0) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
