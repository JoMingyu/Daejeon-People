package com.planb.support.user;

import io.vertx.ext.web.RoutingContext;

public interface AccountManageable {
	public void register(String id, String password);
	public void login(String id, String password);
	public String getIdFromSession(RoutingContext context);
	public String createSession();
	public String registerSessionId(RoutingContext context, String id);
}
