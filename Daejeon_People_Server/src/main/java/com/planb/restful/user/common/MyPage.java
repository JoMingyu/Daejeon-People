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
	private UserManager userManager;
	
	public MyPage() {
		userManager = new UserManager();
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		AES256 aes = UserManager.getAES256Instance();
		JSONObject response = new JSONObject();
		
		if(!userManager.isLogined(ctx)) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
			return;
		}
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		ResultSet userInfo = database.executeQuery("SELECT * FROM account WHERE id='", clientId, "'");
		try {
			userInfo.next();
			response.put("email", aes.decrypt(userInfo.getString("email")));
			response.put("phone", aes.decrypt(userInfo.getString("phone_number")));
			response.put("name", aes.decrypt(userInfo.getString("name")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
