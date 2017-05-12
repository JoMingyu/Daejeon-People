package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel/accept", method = HttpMethod.POST)
public class AcceptTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 여행 초대를 수락한 사람
		String topic = ctx.request().getFormAttribute("topic");
		
		database.executeUpdate("DELETE FROM travel_invites WHERE dst_id='", clientId, "' AND topic='", topic, "'");
		ResultSet rs = database.executeQuery("SELECT * FROM travels WHERE topic='", topic, "'");
		try {
			rs.next();
			String title = rs.getString("title");
			
			database.executeUpdate("INSERT INTO travels VALUES('", topic, "', '", title, "', '", clientId, "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
