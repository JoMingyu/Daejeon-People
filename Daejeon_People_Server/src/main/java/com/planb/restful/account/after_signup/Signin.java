package com.planb.restful.account.after_signup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.planb.support.crypto.AES256;
import com.planb.support.crypto.SHA256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.utilities.MySQL;
import com.planb.support.utilities.SessionUtil;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "계정", summary = "로그인")
@REST(requestBody = "id : String, password : String, keep_login : boolean", successCode = 201, responseHeaders = "Set-Cookie, (key=UserSession)", failureCode = 204)
@Route(uri = "/signin", method = HttpMethod.POST)
public class Signin implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = ctx.request().getFormAttribute("id");
		String password = ctx.request().getFormAttribute("password");
		boolean keepLogin = Boolean.parseBoolean(ctx.request().getFormAttribute("keep_login"));

		if (signin(id, password)) {
			registerSessionId(ctx, keepLogin, id);

			ctx.response().setStatusCode(201).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
	
	private boolean signin(String id, String password) {
		/*
		 * 로그인
		 * 성공 시 true, 실패 시 false
		 */
		String encryptedId = AES256.encrypt(id);
		String encryptedPassword = SHA256.encrypt(password);

		ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE id=? AND password=?", encryptedId, encryptedPassword);
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
	
	private void registerSessionId(RoutingContext ctx, boolean keepLogin, String id) {
		/*
		 * keepLogin 설정에 따라 세션 혹은 쿠키 설정
		 */
		
		String sessionId = getSessionFromId(id);
		// 이미 할당된 session id가 있는지 확인
		
		if(sessionId == null) {
			// 할당된 session id가 없는 경우 create
			sessionId = createSessionId();
		}
		
		// keep_login 설정에 따라 쿠키 또는 세션 put
		if(keepLogin) {
			SessionUtil.createCookie(ctx, "UserSession", sessionId);
		} else {
			SessionUtil.createSession(ctx, "UserSession", sessionId);
		}
	}
	
	private String getSessionFromId(String id) {
		ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE id=?", AES256.encrypt(id));
		try {
			rs.next();
			if(rs.getString("session_id") != null) {
				return rs.getString("session_id");
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String createSessionId() {
		String uuid;
		
		while(true) {
			uuid = UUID.randomUUID().toString();
			ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE session_id=?", SHA256.encrypt(uuid));
			try {
				if(!rs.next()) {
					// 다른 계정과 중복되지 않는 session id
					return uuid;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
