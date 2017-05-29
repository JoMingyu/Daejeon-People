package com.planb.restful.travel.inside;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/map", method = HttpMethod.POST)
public class AddAttractionToMap implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String topic = ctx.request().getFormAttribute("topic");
		int contentId = Integer.parseInt(ctx.request().getFormAttribute("content_id"));

		ResultSet travelAttractionSet = DataBase.executeQuery("SELECT * FROM travel_attractions WHERE topic=? AND content_id=?", topic, contentId);
		try {
			if(travelAttractionSet.next()) {
				ctx.response().setStatusCode(204).end();
				ctx.response().close();
				return;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet clientSet = DataBase.executeQuery("SELECT * FROM account WHERE id=?", clientId);
		ResultSet contentSet = DataBase.executeQuery("SELECT * FROM attractions_basic WHERE content_id=?", contentId);
		try {
			clientSet.next();
			contentSet.next();
			
			String title = contentSet.getString("title");
			String owner = clientSet.getString("name");
			double mapX = contentSet.getDouble("mapx");
			double mapY = contentSet.getDouble("mapy");
			
			DataBase.executeUpdate("INSERT INTO travel_attractions VALUES(?, ?, ?, ?, ?, ?)", topic, contentId, title, owner, mapX, mapY);
			
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
