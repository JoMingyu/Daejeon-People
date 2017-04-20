package com.planb.support.user;

import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;

public class SessionUtil {
	public static void createSession(RoutingContext ctx, String key, String sessionId) {
		ctx.session().put(key, sessionId);
	}
	
	public static void createCookie(RoutingContext ctx, String key, String sessionId) {
		Cookie cookie = Cookie.cookie(key, sessionId);
		cookie.setMaxAge(60 * 60 * 24 * 365);
		cookie.setPath("/");
		ctx.addCookie(cookie);
	}
	
	public static String getRegistedSessionKey(RoutingContext ctx, String key) {
		String sessionKey = null;
		if(ctx.session() != null) {
			sessionKey = ctx.session().get(key);
		} else if(ctx.getCookie(key) != null) {
			sessionKey = ctx.getCookie(key).getValue();
		}
		
		return sessionKey;
	}
}
