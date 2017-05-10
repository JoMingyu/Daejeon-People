package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel", method = HttpMethod.POST)
public class CreateTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 여행 개설자
		
		String notificationKey;
		while(true) {
			notificationKey = UUID.randomUUID().toString();
			ResultSet rs = database.executeQuery("SELECT * FROM chat_list WHERE notification_key='", notificationKey, "'");
			try {
				if(!rs.next()) {
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
