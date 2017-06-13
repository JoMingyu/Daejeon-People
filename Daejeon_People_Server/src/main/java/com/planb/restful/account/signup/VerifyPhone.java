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

@API(functionCategory = "회원가입", summary = "핸드폰 인증번호 확인")
@REST(requestBody = "number : String, code : String", successCode = 201, failureCode = 204)
@Route(uri = "/signup/phone/verify", method = HttpMethod.POST)
public class VerifyPhone implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String phoneNumber = ctx.request().getFormAttribute("number");
		String code = ctx.request().getFormAttribute("code");
		
		ctx.response().setStatusCode(verifyPhone(phoneNumber, code)).end();
		ctx.response().close();
	}
	
	private int verifyPhone(String phoneNumber, String code) {
		String encryptedPhoneNumber = AES256.encrypt(phoneNumber);

		ResultSet rs = MySQL.executeQuery("SELECT * FROM phone_verify_codes WHERE phone_number=? AND code=?", encryptedPhoneNumber, code);
		try {
			if (rs.next()) {
				MySQL.executeUpdate("DELETE FROM phone_verify_codes WHERE phone_number=? AND code=?", encryptedPhoneNumber, code);
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
