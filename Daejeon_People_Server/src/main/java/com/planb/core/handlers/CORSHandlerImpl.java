package com.planb.core.handlers;

import com.google.common.net.HttpHeaders;

import io.vertx.ext.web.RoutingContext;

class CORSHandlerImpl implements CORSHandler {
	@Override
	public void handle(RoutingContext ctx) {
        ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Cookie, Origin, X-Requested-With, Content-Type");
        ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, PATCH, GET, DELETE, OPTIONS, HEAD, CONNECT");
        ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://52.79.134.200/*");
        ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://52.79.134.200/");
        ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://52.79.134.200");
        ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        ctx.next();
    }
}
