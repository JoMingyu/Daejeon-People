package com.planb.restful.account.after_signup;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "아이디 찾기", summary = "이메일 인증번호 발송")
@REST(requestBody = "email : String, name : String", successCode = 201, failureCode = 204, etc = "입력한 데이터의 계정이 존재하지 않을 경우 fail")
@Route(uri = "/find/id/demand", method = HttpMethod.POST)
public class FindId_DemandCode implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		UserManager userManager = new UserManager();
		String email = ctx.request().getFormAttribute("email");
		String name = ctx.request().getFormAttribute("name");
		
		ctx.response().setStatusCode(userManager.findIdDemand(email, name)).end();
		ctx.response().close();
	}
}
