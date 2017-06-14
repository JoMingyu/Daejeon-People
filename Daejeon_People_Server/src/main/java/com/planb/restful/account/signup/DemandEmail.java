package com.planb.restful.account.signup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.utilities.Mail;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "회원가입", summary = "이메일 인증번호 발송")
@REST(requestBody = "email : String", successCode = 201, failureCode = 204, etc = "이메일 중복 시 fail")
@Route(uri = "/signup/email/demand", method = HttpMethod.POST)
public class DemandEmail implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		
		ctx.response().setStatusCode(demandEmail(email)).end();
		ctx.response().close();
	}

	private int demandEmail(String email) {
		/*
		 * 이메일 전송
		 */
		if(checkEmailExists(email)) {
			return 204;
		}
		
		String encryptedEmail = AES256.encrypt(email);
		// 이메일 암호화

		Random random = new Random();
		String code = String.format("%06d", random.nextInt(1000000));
		// 이메일 인증코드 생성
		
		MySQL.executeUpdate("DELETE FROM email_verify_codes WHERE email=?", encryptedEmail);
		MySQL.executeUpdate("INSERT INTO email_verify_codes VALUES(?, ?)", encryptedEmail, code);
		// Refresh
		
		Mail.sendMail(email, "[대전사람] 회원가입 인증 코드입니다.", "코드 : " + code);
		// 인증코드 전송
		
		return 201;
	}
	
	private boolean checkEmailExists(String email) {
		ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE email=?", AES256.encrypt(email));
		try {
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
