package com.planb.restful.user.friend;

import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/friend/delete", method = HttpMethod.POST)
public class DeleteFriend implements Handler<RoutingContext> {
	/*
	 * 친구 삭제
	 * REST 아키텍처에 따라 DELETE /friend로 두어야 하지만 보안 상의 문제로 인해 POST로 결정
	 */
	@Override
	public void handle(RoutingContext ctx) {
		
	}
}
