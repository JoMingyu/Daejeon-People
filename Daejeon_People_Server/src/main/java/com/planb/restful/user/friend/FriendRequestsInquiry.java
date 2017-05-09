package com.planb.restful.user.friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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
		JSONArray response = new JSONArray();
		
		String dst = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet requests = database.executeQuery("SELECT src_id FROM friend_requests WHERE dst_id='", dst, "'");
		// 자신을 타겟으로 한 친구 요청 목록
		
		Map<String, String> requestMap = new HashMap<String, String>();
		try {
			while(requests.next()) {
				requestMap.put(requests.getString("src_id"), requests.getString("date"));
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
				JSONObject requesterInfo = new JSONObject();
				requesterInfo.put("phone_number", requesterSet.getString("phone_number"));
				requesterInfo.put("email", requesterSet.getString("email"));
				requesterInfo.put("name", requesterSet.getString("name"));
				requesterInfo.put("date", requestMap.get(requesterId));
				response.put(requesterInfo);
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
