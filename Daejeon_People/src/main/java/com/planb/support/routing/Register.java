package com.planb.support.routing;

import java.util.Set;

import org.reflections.Reflections;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Register {
	@SuppressWarnings("unchecked")
	public static void route(Router router, String... packages) {
		// 리플렉션으로 탐색할 패키지들을 가변인자로 받아옴
		for(String p: packages) {
			Reflections reflections = new Reflections(p);
			// 패키지에 대한 리플렉션
			
			Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Route.class);
			// Route 어노테이션이 선언된 클래스들
			
			for(Class<?> c: annotatedClasses) {
				Route annotation = c.getAnnotation(Route.class);
				
				try {
					router.route(annotation.method(), annotation.path()).handler((Handler<RoutingContext>) c.newInstance());
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} // Inner for-each
		} // Outer for-each
	}
}
