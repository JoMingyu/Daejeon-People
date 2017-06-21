package com.planb.core.handlers;

import com.planb.support.utilities.Log;

import io.vertx.ext.web.RoutingContext;

public class LogHandlerImpl implements LogHandler {
	@Override
	public void handle(RoutingContext ctx) {
		Log.Req(ctx.request().host() + " " + ctx.request().uri());
		
		ctx.next();
	}
}
