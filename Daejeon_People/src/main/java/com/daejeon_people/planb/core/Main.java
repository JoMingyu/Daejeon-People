package com.daejeon_people.planb.core;

import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
    	Vertx vertx = Vertx.vertx();
    	vertx.deployVerticle(new DaejeonPeople());
    }
}
