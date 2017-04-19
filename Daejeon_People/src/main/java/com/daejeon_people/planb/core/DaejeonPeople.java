package com.daejeon_people.planb.core;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class DaejeonPeople extends AbstractVerticle {
	public void start() throws Exception {
		Router router = Router.router(vertx);
		router.route("/").handler(context -> {
			System.out.println(context.request().params());
		});
		
		vertx.createHttpServer().requestHandler(router::accept).listen(80);
	}
}
