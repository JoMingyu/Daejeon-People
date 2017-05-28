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

@Route(uri = "/user", method = HttpMethod.POST)
public class AnotherUser implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		AES256 aes = UserManager.getAES256Instance();
		String id = ctx.request().getFormAttribute("id");
		
		ResultSet friendInfoSet = database.executeQuery("SELECT * FROM account WHERE id='", id, "'");
		try {
			friendInfoSet.next();
			JSONObject friendInfo = new JSONObject();
			friendInfo.put("id", id);
			friendInfo.put("phone_number", aes.decrypt(friendInfoSet.getString("phone_number")));
			friendInfo.put("email", aes.decrypt(friendInfoSet.getString("email")));
			friendInfo.put("name", aes.decrypt(friendInfoSet.getString("name")));
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
