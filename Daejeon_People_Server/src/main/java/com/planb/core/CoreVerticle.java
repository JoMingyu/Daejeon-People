package com.planb.core;

import com.planb.api.support.ParserThread;
import com.planb.support.routing.Register;

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
		
		router.route().handler(BodyHandler.create().setUploadsDirectory("upload-files"));
		/**
		 * @brief
		 * public interface BodyHandler
		 * A handler which gathers the entire request body and sets it on the RoutingContext.
		 * 
		 * @see
		 * http://vertx.io/docs/apidocs/io/vertx/ext/web/handler/BodyHandler.html
		 */
		
		router.route().handler(CookieHandler.create());
		/**
		 * @brief
		 * public interface CookieHandler
		 * A handler which decodes cookies from the request,
		 * makes them available in the RoutingContext and writes them back in the response.
		 * 
		 * @see
		 * http://vertx.io/docs/apidocs/io/vertx/ext/web/handler/CookieHandler.html
		 */
		
		router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
		/**
		 * @brief
		 * public interface SessionHandler
		 * A handler that maintains a Session for each browser session.
		 * 
		 * @see
		 * http://vertx.io/docs/apidocs/io/vertx/ext/web/handler/SessionHandler.html
		 */
		
		Register.route(router, "com.planb.restful");
		router.route().handler(StaticHandler.create());
		/**
		 * @brief
		 * public class StaticHandler
		 * A handler for serving static resources from the file system or classpath.
		 * 
		 * @see
		 * http://vertx.io/docs/apidocs/io/vertx/rxjava/ext/web/handler/StaticHandler.html
		 */
		Thread.sleep(1000);
//		new ParserThread().start();
		
		vertx.createHttpServer().requestHandler(router::accept).listen(80);
		/**
		 * @brief
		 * public interface HttpServer
		 * .requestHandler() : Set the request handler for the server
		 * .listen() : Tell the server to start listening
		 * 
		 * @see
		 * http://vertx.io/docs/apidocs/io/vertx/core/http/HttpServer.html
		 */
		
		// Doxygen comment end.
	}
}
