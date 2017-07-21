package com.planb.restful.account.after_signup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.planb.support.crypto.AES256;
import com.planb.support.crypto.SHA256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.utilities.Mail;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "비밀번호 찾기", summary = "인증번호 확인 후 이메일로 임시 비밀번호 전송")
@REST(requestBody = "email : String, code : String", successCode = 201, failureCode = 204, etc = "전송한 이메일 인증 코드가 없을 경우 fail")
@Route(uri = "/find/password/verify", method = HttpMethod.POST)
public class FindPassword_VerifyCode implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		String code = ctx.request().getFormAttribute("code");
		
		ctx.response().setStatusCode(findPasswordVerify(email, code)).end();
		ctx.response().close();
	}
	
	private int findPasswordVerify(String email, String code) {
		String encryptedEmail = AES256.encrypt(email);
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
		try {
			if (rs != null && rs.next()) {
				// 전송한 인증 코드가 있을 경우
				
				MySQL.executeUpdate("DELETE FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
				String tempPassword = createTempPassword();
				MySQL.executeUpdate("UPDATE account SET password=? WHERE email=?", SHA256.encrypt(tempPassword), encryptedEmail);
				Mail.sendMail(email, "[대전사람] 비밀번호 찾기 결과입니다.", "임시 비밀번호 : " + tempPassword);
				return 201;
			} else {
				return 204;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 204;
		}
	}
	
	private String createTempPassword() {
		String tempPassword;
		
		while(true) {
			tempPassword = UUID.randomUUID().toString().substring(0, 8);
			ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE password=?", SHA256.encrypt(tempPassword));
			try {
				if(!(rs != null && rs.next())) {
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tempPassword;
	}
}
