package com.planb.support.user;

import io.vertx.ext.web.RoutingContext;

public interface AccountManageable {
	public OperationResult certifyEmail(RoutingContext context, String email);
	public boolean checkIdExists(String id);
	public OperationResult register(String id, String email, String password);
	public OperationResult login(String id, String password);
	public String getIdFromSession(RoutingContext context);
	public String createSession();
	public String registerSessionId(RoutingContext context, String id);
}
