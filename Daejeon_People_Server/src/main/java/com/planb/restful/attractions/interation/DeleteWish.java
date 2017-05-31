package com.planb.restful.attractions.interation;

import com.planb.support.routing.Function;
import com.planb.support.routing.RESTful;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Function(name = "위시리스트", summary = "위시리스트에서 여행지 삭제")
@RESTful(params = "content_id : int", successCode = 200)
@Route(uri = "/wish", method = HttpMethod.DELETE)
public class DeleteWish implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		int contentId = Integer.parseInt(ctx.request().getParam("content_id"));
		
		DataBase.executeUpdate("DELETE FROM wish_list WHERE client_id=? AND content_id=?", clientId, contentId);
		
		ctx.response().setStatusCode(200).end();
		ctx.response().close();
	}
}
