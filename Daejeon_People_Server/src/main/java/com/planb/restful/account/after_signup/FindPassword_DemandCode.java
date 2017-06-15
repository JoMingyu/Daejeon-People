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

@API(functionCategory = "비밀번호 찾기", summary = "이메일 인증번호 발송")
@REST(requestBody = "id : String, email : String, name : String", successCode = 201, failureCode = 204, etc = "계정 정보가 존재하지 않을 경우 fail")
@Route(uri = "/find/password/demand", method = HttpMethod.POST)
public class FindPassword_DemandCode implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String email = ctx.request().getFormAttribute("email");
		String name = ctx.request().getFormAttribute("name");
		
		ctx.response().setStatusCode(findPasswordDemand(id, email, name)).end();
		ctx.response().close();
	}
	
	private int findPasswordDemand(String id, String email, String name) {
		String encryptedId = AES256.encrypt(id);
		String encryptedEmail = AES256.encrypt(email);
		String encryptedName = AES256.encrypt(name);
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE id=? AND email=? AND name=?", encryptedId, encryptedEmail, encryptedName);
		try {
			if(rs.next()) {
				// 계정 정보가 존재할 경우
				
				Random random = new Random();
				String code = String.format("%06d", random.nextInt(1000000));
				// 인증코드 생성
				
				MySQL.executeUpdate("DELETE FROM email_verify_codes WHERE email=?", encryptedEmail);
				MySQL.executeUpdate("INSERT INTO email_verify_codes VALUES(?, ?)", encryptedEmail, code);
				// 인증코드 insert or refresh
				
				Mail.sendMail(email, "[대전사람] 비밀번호 찾기 인증 코드입니다.", "코드 : " + code);
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
