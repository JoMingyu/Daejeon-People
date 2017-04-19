package com.planb.developer.helping;

import com.planb.support.database.DataBase;
import com.planb.support.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/dropper", method = HttpMethod.POST)
public class TableDataDropper implements Handler<RoutingContext> {
	DataBase database;
	public TableDataDropper() {
		database = DataBase.getInstance();
	}
	
	@Override
	public void handle(RoutingContext ctx) {
		database.executeUpdate("DELETE FROM account");
		database.executeUpdate("DELETE FROM verify_codes");
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
