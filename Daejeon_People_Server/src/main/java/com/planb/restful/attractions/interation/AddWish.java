package com.planb.restful.attractions.interation;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/wish/:content_id", method = HttpMethod.POST)
public class AddWish implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		int contentId = Integer.parseInt(ctx.request().getParam("content_id"));
		
		ResultSet content = DataBase.executeQuery("SELECT wish_count FROM attractions_basic WHERE content_id=?", contentId);
		try {
			content.next();
			int wishCount = content.getInt("wish_count");
			DataBase.executeUpdate("UPDATE attractions_basic SET wish_count=? WHERE content_id=?", wishCount + 1, contentId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DataBase.executeUpdate("INSERT INTO wish_list VALUES(?, ?)", clientId, contentId);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
