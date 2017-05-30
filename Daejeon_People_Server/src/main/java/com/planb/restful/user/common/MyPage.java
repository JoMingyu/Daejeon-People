package com.planb.restful.user.common;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/mypage", method = HttpMethod.GET)
public class MyPage implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONObject response = new JSONObject();
		UserManager userManager = new UserManager();
		
		if(!userManager.isLogined(ctx)) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
			return;
		}
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		ResultSet userInfo = DataBase.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		try {
			userInfo.next();
			response.put("email", AES256.decrypt(userInfo.getString("email")));
			response.put("phone_number", userInfo.getString("phone_number") == null ? "전화번호 없음" : AES256.decrypt(userInfo.getString("phone_number")));
			response.put("name", AES256.decrypt(userInfo.getString("name")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
