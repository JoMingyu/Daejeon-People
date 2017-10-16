package com.planb.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.planb.support.utilities.MySQL;
import com.planb.support.utilities.SessionUtil;

import io.vertx.ext.web.RoutingContext;

public class UserManager {

	public static String getEncryptedIdFromSession(RoutingContext ctx) {
		/*
		 * 세션으로부터 암호화된 id get
		 * 유저의 id를 외래키로 갖는 테이블에 접근하기 위해 사용
		 * 객체 생성 없이도 사용할 수 있도록 static
		 */
		String sessionId = SessionUtil.getClientSessionId(ctx, "UserSession");
		String encryptedId = null;

		ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE session_id=?", sessionId);
		try {
			if(rs != null && rs.next()) {
				encryptedId = rs.getString("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return encryptedId;
	}
	
	public static JSONObject getUserInfo(String id) {
		ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", id);
		JSONObject userInfo = new JSONObject();

		try {
			assert userInfoSet != null;
			if(userInfoSet.next()) {
				userInfo.put("id", id);
				userInfo.put("phone_number", userInfoSet.getString("phone_number") == null ? "전화번호 없음" : userInfoSet.getString("phone_number"));
				userInfo.put("email", userInfoSet.getString("email"));
				userInfo.put("name", userInfoSet.getString("name"));
				
				return userInfo;
			} else {
				return null;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			
			return null;
		}
	}

	public static boolean isLogined(RoutingContext ctx) {
		return (getEncryptedIdFromSession(ctx) != null);
	}
}
