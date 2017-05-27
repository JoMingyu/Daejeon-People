package com.planb.restful.user.common;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/profile-image", method = HttpMethod.GET)
public class GetProfileImage implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String encryptedId = UserManager.getEncryptedIdFromSession(ctx);
		
		ctx.response().sendFile("profile-images/" + encryptedId + ".png");
		ctx.response().close();
	}
}
