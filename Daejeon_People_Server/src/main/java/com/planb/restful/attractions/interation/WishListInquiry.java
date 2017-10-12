package com.planb.restful.attractions.interation;

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

@API(functionCategory = "위시리스트", summary = "위시리스트 조회")
@REST(responseBody = "address: String, category: String, content_id: int, image: String, mapx: double, mapy: double, title: String", successCode = 200, failureCode = 204)
@Route(uri = "/wish", method = HttpMethod.GET)
public class WishListInquiry implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet wishList = MySQL.executeQuery("SELECT content_id FROM wish_list WHERE client_id=?", clientId);
		try {
			while(wishList != null ? wishList.next() : false) {
				wishList.getInt("content_id");
				ResultSet contentSet = MySQL.executeQuery("SELECT * FROM attractions_basic WHERE content_id=?", wishList.getInt("content_id"));
				JSONObject contentInfo = new JSONObject();
				assert contentSet != null;
				contentSet.next();
				contentInfo.put("address", contentSet.getString("address"));
				contentInfo.put("category", contentSet.getString("cat3"));
				contentInfo.put("content_id", contentSet.getInt("content_id"));
				contentInfo.put("image", contentSet.getString("image_big_url"));
				contentInfo.put("mapx", contentSet.getDouble("mapx"));
				contentInfo.put("mapy", contentSet.getDouble("mapy"));
				contentInfo.put("title", contentSet.getString("title"));
				response.put(contentInfo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(response.length() == 0) {
			// 위시리스트에 아무 데이터도 없으면
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
