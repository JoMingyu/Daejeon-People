package com.planb.restful.account.signup;

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

@API(functionCategory = "회원가입", summary = "회원가입")
@REST(requestBody = "id : String, password : String, email : String, tel : String(Optional), name : String, registration_id : String", successCode = 201)
@Route(uri = "/signup", method = HttpMethod.POST)
public class Signup implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String password = ctx.request().getFormAttribute("password");
		String email = ctx.request().getFormAttribute("email");
		String tel = ctx.request().formAttributes().contains("tel") ? ctx.request().getFormAttribute("tel") : null;
		String name = ctx.request().getFormAttribute("name");
		String registrationId = ctx.request().getFormAttribute("registration_id");

		signup(id, password, email, tel, name, registrationId);
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
	
	private void signup(String id, String password, String email, String phoneNumber, String name, String registrationId) {
		/*
		 * 회원가입
		 * 중복 체크와 인증은 다른 URI에서 수행
		 */
		String encryptedId = AES256.encrypt(id);
		String encryptedPassword = SHA256.encrypt(password);
		String encryptedEmail = AES256.encrypt(email);
		String encryptedPhoneNumber = phoneNumber == null ? null : AES256.encrypt(phoneNumber);
		// null이면 null, null이 아니면 암호화
		String encryptedName = AES256.encrypt(name);
		String encryptedRegistrationId = AES256.encrypt(registrationId);
		
		if(encryptedPhoneNumber == null) {
			MySQL.executeUpdate("INSERT INTO account(id, password, email, phone_number, name, register_date, registration_id) VALUES(?, ?, ?, null, ?, NOW(), ?)", encryptedId, encryptedPassword, encryptedEmail, encryptedName, encryptedRegistrationId);
		} else {
			MySQL.executeUpdate("INSERT INTO account(id, password, email, phone_number, name, register_date, registration_id) VALUES(?, ?, ?, ?, ?, now(), ?)", encryptedId, encryptedPassword, encryptedEmail, encryptedPhoneNumber, encryptedName, encryptedRegistrationId);
		}
		
		Mail.sendMail(email, "[대전사람] 회원가입을 환영합니다.", "환영환영");
	}
}
