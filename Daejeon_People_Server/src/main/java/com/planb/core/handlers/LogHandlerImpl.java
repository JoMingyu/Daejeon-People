package com.planb.core.handlers;

import com.planb.support.utilities.Log;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public class LogHandlerImpl implements LogHandler {
	@Override
	public void handle(RoutingContext ctx) {
		StringBuilder logStrBuilder = new StringBuilder();
		
		logStrBuilder.append(ctx.request().host()).append(" : ");
		logStrBuilder.append(ctx.request().method()).append(" ");
		logStrBuilder.append(ctx.request().uri()).append("\n");
		
		if(ctx.request().method() != HttpMethod.GET) {
			// Parameters show in Request URI
			logStrBuilder.append("Body - ").append(ctx.request().formAttributes());
		}
		
		Log.request(logStrBuilder.toString());
		
		ctx.next();
	}
}
