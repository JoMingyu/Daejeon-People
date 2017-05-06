package com.planb.restful.user.common;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/mypage", method = HttpMethod.POST)
public class MyPage implements Handler<RoutingContext> {
	UserManager userManager;
	
	public MyPage() {
		userManager = new UserManager();
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		System.out.println("Branched");
		if(!userManager.isLogined(ctx)) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
			return;
		}
		
		ctx.response().setStatusCode(200).end();
		ctx.response().close();
//		String encryptedId = userManager.getEncryptedIdFromSession(ctx);
		// Id를 통해 마이페이지에 들어갈 정보들 response
	}
}
