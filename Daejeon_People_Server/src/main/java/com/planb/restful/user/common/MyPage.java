package com.planb.restful.user.common;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "사용자", summary = "마이페이지")
@REST(responseBody = "email : String, phone_number : String, name : String", successCode = 200, failureCode = 204)
@Route(uri = "/mypage", method = HttpMethod.GET)
public class MyPage implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		if(UserManager.isLogined(ctx)) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
			return;
		}
		
		JSONObject response = new JSONObject();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		try {
			userInfoSet.next();
			response.put("email", AES256.decrypt(userInfoSet.getString("email")));
			response.put("phone_number", userInfoSet.getString("phone_number") == null ? "전화번호 없음" : AES256.decrypt(userInfoSet.getString("phone_number")));
			response.put("name", AES256.decrypt(userInfoSet.getString("name")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
