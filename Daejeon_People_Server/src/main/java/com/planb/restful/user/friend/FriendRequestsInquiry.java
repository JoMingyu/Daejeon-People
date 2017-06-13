package com.planb.restful.user.friend;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
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

@API(functionCategory = "친구", summary = "친구 요청 목록 조회")
@REST(responseBody = "requester_id : String, date : String, phone_number : String, email : String, name : String", successCode = 200, failureCode = 204)
@Route(uri = "/friend/request", method = HttpMethod.GET)
public class FriendRequestsInquiry implements Handler<RoutingContext> {
	// 친구요청 목록 조회
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet requestSet = MySQL.executeQuery("SELECT src_id, date FROM friend_requests WHERE dst_id=?", clientId);
		// 자신을 타겟으로 한 친구 요청 목록
		
		try {
			while(requestSet.next()) {
				ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", requestSet.getString("src_id"));
				// 요청자의 id와 요청 날짜
				userInfoSet.next();
				
				JSONObject requesterInfo = new JSONObject();
				requesterInfo.put("requester_id", requestSet.getString("src_id"));
				requesterInfo.put("date", requestSet.getString("date"));
				requesterInfo.put("phone_number", AES256.decrypt(userInfoSet.getString("phone_number")));
				requesterInfo.put("email", AES256.decrypt(userInfoSet.getString("email")));
				requesterInfo.put("name", AES256.decrypt(userInfoSet.getString("name")));
				
				response.put(requesterInfo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(response.length() == 0) {
			// 아무 친구 요청도 없으면
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
