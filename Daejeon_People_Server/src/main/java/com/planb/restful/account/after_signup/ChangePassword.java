package com.planb.restful.account.after_signup;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(functionCategory = "계정", summary = "비밀번호 변경")
@RESTful(requestBody = "id : String, current_password : String, new_password : String", successCode = 201, failureCode = 204)
@Route(uri = "/change/password", method = HttpMethod.POST)
public class ChangePassword implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		UserManager userManager = new UserManager();
		String id = ctx.request().getFormAttribute("id");
		String currentPassword = ctx.request().getFormAttribute("current_password");
		String newPassword = ctx.request().getFormAttribute("new_password");
		
		if(userManager.changePassword(id, currentPassword, newPassword)) {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}
