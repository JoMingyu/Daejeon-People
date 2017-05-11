package com.planb.restful.user.common;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/mypage", method = HttpMethod.GET)
public class MyPage implements Handler<RoutingContext> {
	private UserManager userManager;
	
	public MyPage() {
		userManager = new UserManager();
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		if(!userManager.isLogined(ctx)) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
			return;
		}
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ctx.response().setStatusCode(200).end();
		ctx.response().close();
		// Id를 통해 마이페이지에 들어갈 정보들 response
	}
}
