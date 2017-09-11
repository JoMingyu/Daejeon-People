package com.planb.restful.account.after_signup;

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

@API(functionCategory = "아이디 찾기", summary = "이메일 인증번호 발송")
@REST(requestBody = "email : String", successCode = 201, failureCode = 204, etc = "계정 정보가 존재하지 않을 경우 fail")
@Route(uri = "/find/id/demand", method = HttpMethod.POST)
public class FindId_DemandCode implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		
		ctx.response().setStatusCode(findIdDemand(email)).end();
		ctx.response().close();
	}
	
	private int findIdDemand(String email) {
		String encryptedEmail = AES256.encrypt(email);
		
		ResultSet rs = MySQL.executeQuery("SELECT id FROM account WHERE email=?", encryptedEmail);
		try {
			if(rs != null && rs.next()) {
				// 계정 정보가 존재할 경우
				
				Random random = new Random();
				String code = String.format("%06d", random.nextInt(1000000));
				// 인증코드 생성
				
				MySQL.executeUpdate("DELETE FROM email_verify_codes WHERE email=?", encryptedEmail);
				MySQL.executeUpdate("INSERT INTO email_verify_codes VALUES(?, ?)", encryptedEmail, code);
				// 인증코드 insert or refresh
				
				Mail.sendMail(email, "[대전사람] 아이디 찾기 인증 코드입니다.", "코드 : " + code);
				// 인증코드 전송
				
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
