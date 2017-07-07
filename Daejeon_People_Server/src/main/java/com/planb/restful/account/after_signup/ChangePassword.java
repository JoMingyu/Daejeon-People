package com.planb.restful.account.after_signup;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.crypto.AES256;
import com.planb.support.crypto.SHA256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "계정", summary = "비밀번호 변경")
@REST(requestBody = "id : String, current_password : String, new_password : String", successCode = 201, failureCode = 204, etc = "계정 정보가 존재하지 않을 경우 fail")
@Route(uri = "/change/password", method = HttpMethod.POST)
public class ChangePassword implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String currentPassword = ctx.request().getFormAttribute("current_password");
		// 현재
		
		String newPassword = ctx.request().getFormAttribute("new_password");
		// 신규
		
		ctx.response().setStatusCode(changePassword(id, currentPassword, newPassword)).end();
		ctx.response().close();
	}
	
	private int changePassword(String id, String currentPassword, String newPassword) {
		ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE id=? AND password=?", AES256.encrypt(id), SHA256.encrypt(currentPassword));
		try {
			if(rs != null ? rs.next() : false) {
				// 계정 정보가 존재할 경우
				
				MySQL.executeUpdate("UPDATE account SET password=? WHERE id=?", SHA256.encrypt(newPassword), AES256.encrypt(id));
				return 201;
			} else {
				return 204;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return 204;
		}
	}
}
