package com.planb.restful.attractions.interation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/wish", method = HttpMethod.GET)
public class WishListInquiry implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet wishList = database.executeQuery("SELECT content_id FROM wish_list WHERE client_id=", clientId);
		List<Integer> contentIdList = new ArrayList<Integer>();
		try {
			while(wishList.next()) {
				contentIdList.add(wishList.getInt("content_id"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(int contentId : contentIdList) {
			ResultSet content = database.executeQuery("SELECT * FROM attractions_basic WHERE content_id=", contentId);
			JSONObject contentInfo = new JSONObject();
			try {
				contentInfo.put("address", content.getString("address"));
				contentInfo.put("category", content.getString("cat3"));
				contentInfo.put("content_id", content.getInt("content_id"));
				contentInfo.put("image", content.getString("image_big_url"));
				contentInfo.put("mapx", content.getDouble("mapx"));
				contentInfo.put("mapy", content.getDouble("mapy"));
				contentInfo.put("title", content.getString("title"));
				response.put(contentInfo);
			} catch (JSONException | SQLException e) {
				e.printStackTrace();
			}
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
