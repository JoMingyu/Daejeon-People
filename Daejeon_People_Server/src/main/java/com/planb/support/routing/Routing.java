package com.planb.support.routing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.planb.support.utilities.Log;

import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Routing {
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
					
					Log.routing(routeAnno.method() + " " + routeAnno.uri());
					// 생성자가 public이 아니면 리플렉션으로 접근 불가능(IllegalStateException)
					
					if(c.isAnnotationPresent(API.class) && c.isAnnotationPresent(REST.class)) {
						// 두 어노테이션이 모두 있는 경우
						API api = c.getAnnotation(API.class);
						REST rest = c.getAnnotation(REST.class);
						resourceList.add(new RESTResource(api.functionCategory(), api.summary(), routeAnno.method().name(), routeAnno.uri(), rest.requestHeaders(), rest.params(), rest.requestBody(), rest.successCode(), rest.responseHeaders(), rest.responseBody(), rest.failureCode(), rest.etc()));
					} else if(c.isAnnotationPresent(API.class) && !c.isAnnotationPresent(REST.class)) {
						// API 어노테이션만 있는 경우
						API api = c.getAnnotation(API.class);
						resourceList.add(new RESTResource(api.functionCategory(), api.summary(), routeAnno.method().name(), routeAnno.uri(), "미정", "미정", "미정", 0, "미정", "미정", 0, "미정"));
					} else if(!c.isAnnotationPresent(API.class) && c.isAnnotationPresent(REST.class)) {
						// REST 어노테이션만 있는 경우
						REST rest = c.getAnnotation(REST.class);
						resourceList.add(new RESTResource("미정", "미정", routeAnno.method().name(), routeAnno.uri(), rest.requestHeaders(), rest.params(), rest.requestBody(), rest.successCode(), rest.responseHeaders(), rest.responseBody(), rest.failureCode(),  rest.etc()));
					} else {
						resourceList.add(new RESTResource("미정", "미정", routeAnno.method().name(), routeAnno.uri(), "미정", "미정", "미정", 0, "미정", "미정", 0, "미정"));
					}
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
			} // Inner for-each
		} // Outer for-each
		
		Collections.sort(resourceList);
//		Arrays.sort(resourceList);
		new Document(resourceList);
	}
}
