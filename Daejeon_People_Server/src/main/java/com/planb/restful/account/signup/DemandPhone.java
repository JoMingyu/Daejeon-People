package com.planb.restful.account.signup;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.SignupManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "회원가입", summary = "핸드폰 인증번호 발송")
@REST(requestBody = "email : String", successCode = 201, etc = "핸드폰 번호 중복 시 fail")
@Route(uri = "/signup/phone/demand", method = HttpMethod.POST)
public class DemandPhone implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		ctx.response().setStatusCode(SignupManager.demandPhone(ctx.request().getFormAttribute("number"))).end();
		ctx.response().close();
	}
}
