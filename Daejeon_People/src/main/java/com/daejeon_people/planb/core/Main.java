package com.daejeon_people.planb.core;

/**
 * @brief
 * 대덕SW마이스터고 2017학년도 SW개발과 전공 프로젝트
 * 2학년 1반 홍초팀 : 대전사람 API 서버
 * 
 * @author JoMingyu(PlanB)
 * @see https://github.com/JoMingyu/Daejeon-People
 * @see https://github.com/JoMingyu
 * @see http://blog.naver.com/city7310
 * 
 * @see Vert.x documentation : http://vertx.io/docs/
 * @see Some Rest with Vert.x : http://vertx.io/blog/some-rest-with-vert-x/
 */

import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
    	Vertx vertx = Vertx.vertx();
    	vertx.deployVerticle(new CoreVerticle());
    }
}
