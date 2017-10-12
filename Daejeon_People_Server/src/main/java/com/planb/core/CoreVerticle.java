package com.planb.core;

import com.planb.core.handlers.CORSHandler;
import com.planb.core.handlers.LogHandler;
import com.planb.parser.support.ParserThread;
import com.planb.support.routing.Routing;
import com.planb.support.utilities.Config;
import com.planb.support.utilities.Log;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class CoreVerticle extends AbstractVerticle {
	public void start() throws Exception {
		Router router = Router.router(vertx);
		int serverPort = Config.getIntValue("serverPort");
		
		router.route().handler(BodyHandler.create().setUploadsDirectory("upload-files"));
		router.route().handler(CookieHandler.create());
		router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
		router.route().handler(CORSHandler.create());
		router.route().handler(LogHandler.create());
		
		Routing.route(router, "com.planb.restful");

		router.route().handler(StaticHandler.create());

//		new ParserThread().start();

		Log.info("Server Started");
		vertx.createHttpServer().requestHandler(router::accept).listen(serverPort);
	}
}
