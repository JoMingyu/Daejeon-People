package com.planb.restful.account.signup;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.SignupManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(functionCategory = "회원가입", summary = "이메일 인증번호 발송")
@RESTful(requestBody = "email : String", successCode = 201)
@Route(uri = "/signup/email/demand", method = HttpMethod.POST)
public class DemandEmail implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		SignupManager.demandEmail(email);

		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
