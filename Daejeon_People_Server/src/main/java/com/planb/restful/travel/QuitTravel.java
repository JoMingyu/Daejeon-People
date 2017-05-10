package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.firebase.Firebase;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel", method = HttpMethod.DELETE)
public class QuitTravel implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String registrationId = UserManager.getRegistrationIdFromSession(ctx);
		String notificationKeyName = ctx.request().getFormAttribute("notification_key_name");
		
		ResultSet rs = database.executeQuery("SELECT * FROM travels WHERE notification_key_name='", notificationKeyName, "'");
		String notificationKey;
		try {
			notificationKey = rs.getString("notificaton_key");
			Firebase.exitGroup(notificationKey, notificationKeyName, registrationId);
			database.executeUpdate("DELETE FROM travels WHERE client_id='", clientId, "' AND notification_key_name='", notificationKeyName, "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(200).end();
		ctx.response().close();
	}
}
