package com.planb.restful.account.after_signup;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;
import com.planb.support.utilities.SessionUtil;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "계정", summary = "로그아웃")
@REST(successCode = 201, failureCode = 204)
@Route(uri = "/logout", method = HttpMethod.POST)
public class Logout implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		if(UserManager.isLogined(ctx)) {
			logout(ctx);
			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
	
	public void logout(RoutingContext ctx) {
		// 로그아웃, 세션 또는 쿠키에 있는 session id 삭제
		
		MySQL.executeUpdate("UPDATE account SET session_id=null WHERE id=?", UserManager.getEncryptedIdFromSession(ctx));
		SessionUtil.removeSession(ctx, "UserSession");
	}
}
