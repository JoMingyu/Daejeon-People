package com.planb.restful.account.after_signup;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.utilities.Mail;
import com.planb.support.utilities.MailSubjects;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "아이디 찾기", summary = "인증번호 확인 후 이메일로 아이디 전송")
@REST(requestBody = "email : String, code : String", successCode = 201, failureCode = 204)
@Route(uri = "/find/id/verify", method = HttpMethod.POST)
public class FindId_VerifyCode implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String email = ctx.request().getFormAttribute("email");
		String code = ctx.request().getFormAttribute("code");
		
		ctx.response().setStatusCode(findIdVerify(email, code)).end();
		ctx.response().close();
	}
	
	private int findIdVerify(String email, String code) {
		String encryptedEmail = AES256.encrypt(email);
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
		try {
			if(rs.next()) {
				MySQL.executeUpdate("DELETE FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
				rs = MySQL.executeQuery("SELECT * FROM account WHERE email=?", encryptedEmail);
				rs.next();
				String decryptedId = AES256.decrypt(rs.getString("id"));
				Mail.sendMail(email, MailSubjects.FIND_ID_RESULT_SUBJECT.getName(), "ID : " + decryptedId);
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
