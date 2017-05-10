package com.planb.restful.account;

import com.planb.support.routing.Route;
import com.planb.support.user.SignupManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/signup", method = HttpMethod.POST)
public class Signup implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String password = ctx.request().getFormAttribute("password");
		String email = ctx.request().getFormAttribute("email");
		String tel = null;
		if(ctx.request().formAttributes().contains("tel")) {
			tel = ctx.request().getFormAttribute("tel");
		}
		String name = ctx.request().getFormAttribute("name");
		String registrationId = ctx.request().getFormAttribute("registration_id");

		SignupManager.signup(id, password, email, tel, name, registrationId);

		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
