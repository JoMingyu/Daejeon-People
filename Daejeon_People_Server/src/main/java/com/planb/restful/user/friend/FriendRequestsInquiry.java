package com.planb.restful.user.friend;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.crypto.AES256;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/friend/request", method = HttpMethod.GET)
public class FriendRequestsInquiry implements Handler<RoutingContext> {
	// 친구요청 목록 조회
	@Override
	public void handle(RoutingContext ctx) {
		AES256 aes = UserManager.getAES256Instance();
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet requestSet = DataBase.executeQuery("SELECT src_id, date FROM friend_requests WHERE dst_id=?", clientId);
		// 자신을 타겟으로 한 친구 요청 목록
		
		try {
			while(requestSet.next()) {
				ResultSet requesterSet = DataBase.executeQuery("SELECT * FROM account WHERE id=?", requestSet.getString("src_id"));
				// 요청자의 id와 요청 날짜
				JSONObject requester = new JSONObject();
				requester.put("requester_id", requestSet.getString("src_id"));
				requester.put("date", requestSet.getString("date"));
				requester.put("phone_number", aes.decrypt(requesterSet.getString("phone_number")));
				requester.put("email", aes.decrypt(requesterSet.getString("email")));
				requester.put("name", aes.decrypt(requesterSet.getString("name")));
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
