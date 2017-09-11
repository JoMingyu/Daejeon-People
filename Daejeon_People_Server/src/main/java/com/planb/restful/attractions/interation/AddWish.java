package com.planb.restful.attractions.interation;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "위시리스트", summary = "위시리스트 추가")
@REST(requestBody = "content_id : int", successCode = 201)
@Route(uri = "/wish", method = HttpMethod.POST)
public class AddWish implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		int contentId = Integer.parseInt(ctx.request().getFormAttribute("content_id"));
		
		ResultSet contentSet = MySQL.executeQuery("SELECT wish_count FROM attractions_basic WHERE content_id=?", contentId);
		try {
			assert contentSet != null;
			contentSet.next();
			MySQL.executeUpdate("UPDATE attractions_basic SET wish_count=? WHERE content_id=?", contentSet.getInt("wish_count") + 1, contentId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		MySQL.executeUpdate("INSERT INTO wish_list VALUES(?, ?)", clientId, contentId);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
