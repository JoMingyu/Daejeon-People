package com.planb.support.routing;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.vertx.core.http.HttpMethod;

@Retention(RUNTIME)
@Target(TYPE)
public @interface Route {
	String uri();
	HttpMethod method();
}
