package com.planb.support.user;

import com.planb.support.database.DataBase;

import io.vertx.ext.web.RoutingContext;

public class UserManager implements AccountManageable {
	private DataBase database = DataBase.getInstance();
	
	@Override
	public void register(String id, String password) {
	}

	@Override
	public void login(String id, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getIdFromSession(RoutingContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registerSessionId(RoutingContext context, String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
