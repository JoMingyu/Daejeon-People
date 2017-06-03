package com.planb.restful.account.signup;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.SignupManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "회원가입", summary = "이메일 인증번호 확인")
@REST(requestBody = "email : String, code : String", successCode = 201, failureCode = 204)
@Route(uri = "/signup/email/verify", method = HttpMethod.POST)
public class VerifyEmail implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		String code = ctx.request().getFormAttribute("code");
		
		ctx.response().setStatusCode(SignupManager.verifyEmail(email, code));
		ctx.response().close();
	}
}
