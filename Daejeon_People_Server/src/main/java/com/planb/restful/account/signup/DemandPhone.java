package com.planb.restful.account.signup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "회원가입", summary = "핸드폰 인증번호 발송")
@REST(requestBody = "number : String", successCode = 201, etc = "핸드폰 번호 중복 시 fail")
@Route(uri = "/signup/phone/demand", method = HttpMethod.POST)
public class DemandPhone implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String phoneNumber = ctx.request().getFormAttribute("number");
		
		ctx.response().setStatusCode(demandPhone(phoneNumber)).end();
		ctx.response().close();
	}
	
	private int demandPhone(String phoneNumber) {
		// 핸드폰 인증번호 전송
		
		if(checkPhoneNumberExists(phoneNumber)) {
			// 핸드폰 번호 중복 체크
			return 204;
		}
		
		String encryptedPhoneNumber = AES256.encrypt(phoneNumber);
		// 핸드폰 번호 암호화
		
		Random random = new Random();
		String code = String.format("%06d", random.nextInt(1000000));
		// 인증코드 생성
		
		MySQL.executeUpdate("DELETE FROM phone_verify_codes WHERE phone_number=?", encryptedPhoneNumber);
		MySQL.executeUpdate("INSERT INTO phone_verify_codes VALUES(?, ?)", encryptedPhoneNumber, code);
		// 인증코드 insert or refresh
		
		// 인증코드 전송(보류)
		
		return 201;
	}
	
	private boolean checkPhoneNumberExists(String phoneNumber) {
		ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE phone_number=?", AES256.encrypt(phoneNumber));
		try {
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
