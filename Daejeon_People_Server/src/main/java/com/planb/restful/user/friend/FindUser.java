package com.planb.restful.user.friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "친구", summary = "사용자 검색")
@REST(params = "keyword : String", responseBody = "email : String, name : String, id : String, friend_requested : boolean, friend_recieved : boolean", successCode = 200, failureCode = 204)
@Route(uri = "/find_user", method = HttpMethod.GET)
public class FindUser implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		String keyword = ctx.request().getParam("keyword");
		/*
		 * 키워드는 핸드폰 번호, 또는 이메일일 수 있음
		 * 핸드폰 번호 : 010xxxxxxxx or xxxxxxxx
		 * 이메일 : ????@???.???
		 */
		if(Pattern.matches("[0-9]+", keyword)) {
			/*
			 * 모두 숫자로 이루어진 7~12자리 사이의 전화번호 형태일 때
			 * 클라이언트 측에서 검증하겠지만 만일의 사태 대비
			 * 앞에 010이 없는 경우 붙여줌
			 */
			if(keyword.length() < 9) {
				if(!keyword.startsWith("010")) {
					keyword = "010" + keyword;
				}
			}
			
		}
		
		keyword = AES256.encrypt(keyword);
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM account WHERE email=? OR phone_number=?", keyword, keyword);
		try {
			if(rs.next()) {
				JSONObject response = new JSONObject();
				
				if(rs.getString("id").equals(clientId)) {
					ctx.response().setStatusCode(205).end();
					ctx.response().close();
					return;
				}
				
				response.put("email", rs.getString("email"));
				response.put("name", rs.getString("name"));
				response.put("id", rs.getString("id"));
				
				ResultSet friendSet = MySQL.executeQuery("SELECT * FROM friend_requests WHERE src_id=? AND dst_id=?", clientId, rs.getString("id"));
				if(friendSet.next()) {
					response.put("friend_requested", true);
				} else {
					response.put("friend_requested", false);
				}
				
				friendSet = MySQL.executeQuery("SELECT * FROM friend_requests WHERE src_id=? AND dst_id=?", rs.getString("id"), clientId);
				if(friendSet.next()) {
					response.put("friend_recieved", true);
				} else {
					response.put("friend_recieved", false);
				}
				
				ctx.response().setStatusCode(200);
				ctx.response().end(response.toString());
				ctx.response().close();
			} else {
				// Can't find user
				ctx.response().setStatusCode(204).end();
				ctx.response().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
