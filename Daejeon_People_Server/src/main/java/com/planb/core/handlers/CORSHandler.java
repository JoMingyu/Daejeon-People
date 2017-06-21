package com.planb.core.handlers;

import com.google.common.net.HttpHeaders;

import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;

public class CORSHandler {
	public static Handler<RoutingContext> create() {
        return new CORS();
    }

    private static class CORS implements Handler<RoutingContext> {
        @Override
        public void handle(RoutingContext ctx) {
            ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Cookie, Origin, X-Requested-With, Content-Type");
            ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, PUT, PATCH, GET, DELETE, OPTIONS, HEAD, CONNECT");
            ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://dsm2015.cafe24.com/*");
            ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://dsm2015.cafe24.com/");
            ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://dsm2015.cafe24.com");
            ctx.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            ctx.next();
        }
    }
}
