package com.planb.restful.account.signup;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.SignupManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "회원가입", summary = "핸드폰 인증번호 발송")
@REST(requestBody = "email : String", successCode = 201)
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
