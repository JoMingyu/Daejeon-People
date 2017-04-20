package com.planb.support.user;

import com.planb.support.database.DataBase;

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
	
	public static void removeSession(RoutingContext ctx, String key, String encryptedId) {
		if(ctx.session().get(key) != null) {
			ctx.session().remove(key);
		}
		if(ctx.getCookie(key) != null) {
			ctx.getCookie(key).setMaxAge(0);
		}
	}
	
	public static String getRegistedSessionId(RoutingContext ctx, String key) {
		String sessionId = null;
		if(ctx.session().get(key) != null) {
			sessionId = ctx.session().get(key);
		} else if(ctx.getCookie(key) != null) {
			sessionId = ctx.getCookie(key).getValue();
		}
		
		return sessionId;
	}
}
