package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.json.JSONObject;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel", method = HttpMethod.POST)
public class CreateTravel implements Handler<RoutingContext> {
	// 여행 개설
	@Override
	public void handle(RoutingContext ctx) {
		JSONObject response = new JSONObject();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 여행 개설자
		String title = ctx.request().getFormAttribute("title");
		
		String topic;
		while(true) {
			topic = UUID.randomUUID().toString();
			ResultSet rs = DataBase.executeQuery("SELECT * FROM travels WHERE topic='", topic, "'");
			try {
				if(!rs.next()) {
					response.put("topic", topic);
					DataBase.executeUpdate("INSERT INTO travels VALUES('", topic, "', '", title, "', '", clientId, "')");
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		ctx.response().setStatusCode(201);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
}
