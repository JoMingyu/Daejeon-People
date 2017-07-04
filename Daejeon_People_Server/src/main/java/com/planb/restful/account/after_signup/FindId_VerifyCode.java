package com.planb.restful.account.after_signup;

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

@API(functionCategory = "아이디 찾기", summary = "인증번호 확인 후 이메일로 아이디 전송")
@REST(requestBody = "email : String, code : String", successCode = 201, failureCode = 204, etc = "전송한 이메일 인증 코드가 없을 경우 fail")
@Route(uri = "/find/id/verify", method = HttpMethod.POST)
public class FindId_VerifyCode implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		String code = ctx.request().getFormAttribute("code");
		
		String encryptedEmail = AES256.encrypt(email);
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
		try {
			if(rs.next()) {
				// 전송한 인증 코드가 있을 경우
				
				MySQL.executeUpdate("DELETE FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
				rs = MySQL.executeQuery("SELECT * FROM account WHERE email=?", encryptedEmail);
				rs.next();
				
				ctx.response().setStatusCode(201);
				ctx.response().end(rs.getString("id"));
			} else {
				ctx.response().setStatusCode(204).end();
				ctx.response().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
