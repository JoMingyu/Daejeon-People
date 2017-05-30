package com.planb.support.routing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.planb.support.utilities.Log;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Register {
	private static List<RESTResource> resourceList = new ArrayList<RESTResource>();
	
	@SuppressWarnings("unchecked")
	public static void route(Router router, String... packages) {
		// 리플렉션으로 탐색할 패키지들을 가변인자로 받아옴
		for(String p: packages) {
			Reflections reflections = new Reflections(p);
			// 패키지에 대한 리플렉션
			
			Set<Class<?>> routeAnnotatedClasses = reflections.getTypesAnnotatedWith(Route.class);
			// Route 어노테이션이 선언된 클래스들
			
			for(Class<?> c : routeAnnotatedClasses) {
				Route routeAnno = c.getAnnotation(Route.class);
				
				try {
					router.route(routeAnno.method(), routeAnno.uri()).handler((Handler<RoutingContext>) c.newInstance());
					// Routing
					
					Log.R(routeAnno.method() + " " + routeAnno.uri());
					// 생성자가 public이 아니면 리플렉션으로 접근 불가능(IllegalStateException)
					
					if(c.isAnnotationPresent(Function.class) && c.isAnnotationPresent(RESTful.class)) {
						Function functionAnno = c.getAnnotation(Function.class);
						RESTful restfulAnno = c.getAnnotation(RESTful.class);
						resourceList.add(new RESTResource(functionAnno.name(), functionAnno.summary(), routeAnno.method().name(), routeAnno.uri(), restfulAnno.requestHeaders(), restfulAnno.params(), restfulAnno.requestBody(), restfulAnno.successCode(), restfulAnno.responseHeaders(), restfulAnno.responseBody(), restfulAnno.failureCode()));
					} else {
						resourceList.add(new RESTResource("미정", "미정", routeAnno.method().name(), routeAnno.uri(), "미정", "미정", "미정", 0, "미정", "미정", 0));
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} // Inner for-each
		} // Outer for-each
		
		new Document(resourceList);
	}
}
