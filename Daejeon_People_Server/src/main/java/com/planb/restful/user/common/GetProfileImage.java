package com.planb.restful.user.common;

import java.io.File;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(functionCategory = "사용자", summary = "프로필 사진 조회")
@RESTful(responseBody = "프로필 이미지 파일", successCode = 200, failureCode = 204)
@Route(uri = "/profile-image", method = HttpMethod.GET)
public class GetProfileImage implements Handler<RoutingContext> {
	@SuppressWarnings("unused")
	@Override
	public void handle(RoutingContext ctx) {
		String encryptedId = UserManager.getEncryptedIdFromSession(ctx);
		
		File file = new File("profile-images/" + encryptedId + ".png");
		if(file == null) {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
			return;
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().sendFile("profile-images/" + encryptedId + ".png");
		ctx.response().close();
	}
}
