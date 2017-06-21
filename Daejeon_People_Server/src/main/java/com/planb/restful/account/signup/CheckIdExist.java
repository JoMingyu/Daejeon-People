package com.planb.restful.account.signup;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "회원가입", summary = "ID 중복 체크")
@REST(requestBody = "id : String", successCode = 201, failureCode = 204)
@Route(uri = "/signup/id/check", method = HttpMethod.POST)
public class CheckIdExist implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		
		ctx.response().setStatusCode(checkIdExists(id)).end();
		ctx.response().close();
	}
	
	private int checkIdExists(String id) {
		ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE id=?", AES256.encrypt(id));
		try {
			if (rs.next()) {
				return 204;
			} else {
				return 201;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 204;
		}
	}
}
