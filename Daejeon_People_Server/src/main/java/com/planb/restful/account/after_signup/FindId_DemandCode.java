package com.planb.restful.account.after_signup;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(functionCategory = "아이디 찾기", summary = "이메일 인증번호 발송")
@RESTful(requestBody = "email : String, name : String", successCode = 201, failureCode = 204)
@Route(uri = "/find/id/demand", method = HttpMethod.POST)
public class FindId_DemandCode implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		UserManager userManager = new UserManager();
		String email = ctx.request().getFormAttribute("email");
		String name = ctx.request().getFormAttribute("name");
		
		if(userManager.findIdDemand(email, name)) {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}
