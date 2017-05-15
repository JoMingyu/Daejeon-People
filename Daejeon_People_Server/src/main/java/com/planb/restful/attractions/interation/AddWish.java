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
		DataBase database = DataBase.getInstance();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		int contentId = Integer.valueOf(ctx.request().getParam("content_id"));
		
		ResultSet content = database.executeQuery("SELECT wish_count FROM attractions_basic WHERE content_id=", contentId);
		try {
			content.next();
			int wishCount = content.getInt("wish_count");
			database.executeUpdate("UPDATE attractions_basic SET wish_count=", wishCount + 1, " WHERE content_id=", contentId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		database.executeUpdate("INSERT INTO wish_list VALUES('", clientId, "', ", contentId, ")");
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
