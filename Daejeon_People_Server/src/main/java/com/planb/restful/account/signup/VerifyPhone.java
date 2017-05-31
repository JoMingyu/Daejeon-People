package com.planb.restful.account.signup;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.SignupManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(functionCategory = "회원가입", summary = "핸드폰 인증번호 확인")
@RESTful(requestBody = "email : String, code : String", successCode = 201, failureCode = 204)
@Route(uri = "/signup/phone/verify", method = HttpMethod.POST)
public class VerifyPhone implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String phoneNumber = ctx.request().getFormAttribute("number");
		String code = ctx.request().getFormAttribute("code");
		
		if(SignupManager.verifyPhone(phoneNumber, code)) {
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}
