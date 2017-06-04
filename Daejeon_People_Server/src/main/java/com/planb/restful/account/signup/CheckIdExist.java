package com.planb.restful.account.signup;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.SignupManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(name = "회원가입", summary = "ID 중복 체크")
@RESTful(requestBody = "id : String", successCode = 201, failureCode = 204)
@Route(uri = "/signup/id/check", method = HttpMethod.POST)
public class CheckIdExist implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");

		if(SignupManager.checkIdExists(id)) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		}
	}
}
