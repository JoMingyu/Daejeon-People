package com.planb.restful.attractions.main;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행지 정보", summary = "메인 페이지를 위한 정보")
@REST(params = "popular_count: int, monthly_count: int", responseBody = "content_id : int, title : String, wish : boolean, wish_count : int, address : String, category : String, image : String, mapx : double, mapy : double", successCode = 200)
@Route(uri = "/main-page", method = HttpMethod.GET)
public class MainPage implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		int popularCount = Integer.parseInt(ctx.request().getParam("popular_count"));
		int monthlyCount = Integer.parseInt(ctx.request().getParam("monthly_count"));
		
		ResultSet rsForPopular = MySQL.executeQuery("SELECT * FROM attractions_basic ORDER BY views_count DESC LIMIT 0, ?", popularCount);
		ResultSet rsForMonthly = MySQL.executeQuery("SELECT * FROM attractions_basic ORDER BY wish_count DESC LIMIT 0, ?", monthlyCount);
		
		JSONArray popular = new JSONArray();
		JSONArray monthly = new JSONArray();
		try {
			while(rsForPopular.next() && rsForMonthly.next()) {
				// Popular attraction
				JSONObject popularAttraction = new JSONObject();
				
				popularAttraction.put("content_id", rsForPopular.getInt("content_id"));
				popularAttraction.put("title", rsForPopular.getString("title"));
				popularAttraction.put("image", rsForPopular.getString("image_big_url"));
				popular.put(popularAttraction);
				
				// Monthly attraction
				JSONObject monthlyAttraction = new JSONObject();
				ResultSet wish = MySQL.executeQuery("SELECT * FROM wish_list WHERE client_id=? AND content_id=?", UserManager.getEncryptedIdFromSession(ctx), rsForMonthly.getInt("content_id"));
				monthlyAttraction.put("wish", wish != null ? wish.next() : false);
				monthlyAttraction.put("wish_count", rsForMonthly.getInt("wish_count"));
				monthlyAttraction.put("content_id", rsForMonthly.getInt("content_id"));
				monthlyAttraction.put("title", rsForMonthly.getString("title"));
				monthlyAttraction.put("address", rsForMonthly.getString("address"));
				monthlyAttraction.put("image", rsForMonthly.getString("image_big_url"));
				monthly.put(monthlyAttraction);
			}
			
			JSONObject response = new JSONObject();
			response.put("popular", popular);
			response.put("monthly", monthly);
			
			ctx.response().end(response.toString());
			ctx.response().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
