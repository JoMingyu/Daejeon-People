package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.UserAccountManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/change/password", method = HttpMethod.POST)
public class ChangePassword implements Handler<RoutingContext> {
	UserAccountManager userManager;
	
	public ChangePassword() {
		userManager = new UserAccountManager();
	}

	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String currentPassword = ctx.request().getFormAttribute("current_password");
		String newPassword = ctx.request().getFormAttribute("new_password");
		
		boolean result = userManager.changePassword(id, currentPassword, newPassword);
		if(result) {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}
