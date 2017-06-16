package com.planb.restful.travel;

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

@API(functionCategory = "여행 모드", summary = "초대받은 여행 리스트 조회")
@REST(responseBody = "topic : String, msg : String, date : String, phone_number : String, email : String, name : String, (JSONArray)", successCode = 200, failureCode = 204)
@Route(uri = "/travel/invite", method = HttpMethod.GET)
public class TravelInvitesInquiry implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet inviteSet = MySQL.executeQuery("SELECT * FROM travel_invites WHERE dst_id=?", clientId);
		// 자신을 타겟으로 한 여행 초대 목록
		
		try {
			while(inviteSet.next()) {
				JSONObject invite = new JSONObject();
				ResultSet userInfoSet = MySQL.executeQuery("SELECT * FROM account WHERE id=?", inviteSet.getString("src_id"));
				userInfoSet.next();
				
				invite.put("topic", inviteSet.getString("topic"));
				invite.put("msg", inviteSet.getString("msg"));
				invite.put("date", inviteSet.getString("date"));
				invite.put("phone_number", userInfoSet.getString("phone_number"));
				invite.put("email", userInfoSet.getString("email"));
				invite.put("name", userInfoSet.getString("name"));
				response.put(invite);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		if(response.length() == 0) {
			// 아무 여행 초대도 없으면
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		}
	}
}
