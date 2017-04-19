package com.planb.support.user;

import io.vertx.ext.web.RoutingContext;

public interface AccountManageable {
	public OperationResult checkEmailExists(String email);
	public OperationResult demandEmail(String email);
	public OperationResult verifyEmail(String email, String code);
	
	public OperationResult checkIdExists(String id);
	public void signup(String id, String email, String password);
	public OperationResult signin(String id, String password);
	
	public String getIdFromSession(RoutingContext ctx);
	public String createSession();
	public String registerSessionId(RoutingContext ctx, String id);
}
