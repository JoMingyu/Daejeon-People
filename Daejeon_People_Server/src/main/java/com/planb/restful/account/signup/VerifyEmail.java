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

@API(functionCategory = "회원가입", summary = "이메일 인증번호 확인")
@REST(requestBody = "email : String, code : String", successCode = 201, failureCode = 204, etc = "전송한 이메일 인증 코드가 없을 경우 fail")
@Route(uri = "/signup/email/verify", method = HttpMethod.POST)
public class VerifyEmail implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		String code = ctx.request().getFormAttribute("code");
		
		ctx.response().setStatusCode(verifyEmail(email, code)).end();
		ctx.response().close();
	}
	
	private int verifyEmail(String email, String code) {
		String encryptedEmail = AES256.encrypt(email);

		ResultSet rs = MySQL.executeQuery("SELECT * FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
		try {
			if (rs.next()) {
				// 전송한 인증 코드가 있을 경우
				
				MySQL.executeUpdate("DELETE FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
				return 201;
			} else {
				return 204;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 204;
		}
	}
}
