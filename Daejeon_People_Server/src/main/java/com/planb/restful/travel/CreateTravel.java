package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.json.JSONObject;

import com.planb.support.firebase.Firebase;
import com.planb.support.networking.Response;
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
		DataBase database = DataBase.getInstance();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 여행 개설자
		String registrationId = UserManager.getRegistrationIdFromSession(ctx);
		String title = ctx.request().getFormAttribute("title");
		
		String notificationKeyName;
		while(true) {
			notificationKeyName = UUID.randomUUID().toString();
			ResultSet rs = database.executeQuery("SELECT * FROM travels WHERE notification_key_name='", notificationKeyName, "'");
			try {
				if(!rs.next()) {
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		Response response = Firebase.createGroup(notificationKeyName, registrationId);
		String notificationKey = new JSONObject(response.getResponseBody()).getString("notification_key");
		
		database.executeUpdate("INSERT INTO travels VALUES('", notificationKeyName, "', '", notificationKey, "', '", title, "', '", clientId, "')");
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
