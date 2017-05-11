package com.planb.restful.account.signup;

import com.planb.support.routing.Route;
import com.planb.support.user.SignupManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/signup/phone/demand", method = HttpMethod.POST)
public class DemandPhone implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String phoneNumber = ctx.request().getFormAttribute("number");
		SignupManager.demandPhone(phoneNumber);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
