package com.planb.restful.attractions.interation;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/wish/:content_id", method = HttpMethod.DELETE)
public class DeleteWish implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		int contentId = Integer.valueOf(ctx.request().getParam("content_id"));
		
		database.executeUpdate("DELETE FROM wish_list WHERE client_id='", clientId, "' AND content_id=", contentId);
	}
}
