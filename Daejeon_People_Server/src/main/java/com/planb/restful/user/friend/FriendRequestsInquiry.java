package com.planb.restful.user.friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
		DataBase database = DataBase.getInstance();
		AES256 aes = UserManager.getAES256Instance();
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet requestSet = database.executeQuery("SELECT src_id, date FROM friend_requests WHERE dst_id='", clientId, "'");
		// 자신을 타겟으로 한 친구 요청 목록
		
		Map<String, String> requestMap = new HashMap<String, String>();
		try {
			while(requestSet.next()) {
				requestMap.put(requestSet.getString("src_id"), requestSet.getString("date"));
				// 요청자의 id와 요청 날짜
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(String requesterId : requestMap.keySet()) {
			// 요청자 id 순회
			ResultSet requesterSet = database.executeQuery("SELECT * FROM account WHERE id='", requesterId, "'");
			try {
				requesterSet.next();
				JSONObject requester = new JSONObject();
				requester.put("requester_id", requesterId);
				requester.put("phone_number", aes.decrypt(requesterSet.getString("phone_number")));
				requester.put("email", aes.decrypt(requesterSet.getString("email")));
				requester.put("name", aes.decrypt(requesterSet.getString("name")));
				requester.put("date", requestMap.get(requesterId));
				response.put(requester);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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