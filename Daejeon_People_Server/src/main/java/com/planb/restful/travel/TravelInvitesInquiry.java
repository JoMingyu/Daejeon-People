package com.planb.restful.travel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.AES256;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/travel/invite", method = HttpMethod.GET)
public class TravelInvitesInquiry implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		AES256 aes = UserManager.getAES256Instance();
		JSONArray response = new JSONArray();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		
		ResultSet inviteSet = database.executeQuery("SELECT * FROM travel_invites WHERE dst_id='", clientId, "'");
		// 자신을 타겟으로 한 여행 초대 목록
		
		List<HashMap<String, String>> inviteList = new ArrayList<>();
		
		try {
			while(inviteSet.next()) {
				Map<String, String> inviteInfoMap = new HashMap<String, String>();
				inviteInfoMap.put("requester_id", inviteSet.getString("src_id"));
				inviteInfoMap.put("msg", inviteSet.getString("msg"));
				inviteInfoMap.put("date", inviteSet.getString("date"));
				inviteList.add((HashMap<String, String>) inviteInfoMap);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(HashMap<String, String> inviteInfoMap : inviteList) {
			ResultSet requesterInfo = database.executeQuery("SELECT * FROM account WHERE id='", inviteInfoMap.get("requester_id"), "'");
			JSONObject invite = new JSONObject(inviteInfoMap);
			try {
				invite.put("phone_number", aes.decrypt(requesterInfo.getString("phone_number")));
				invite.put("email", aes.decrypt(requesterInfo.getString("email")));
				invite.put("name", aes.decrypt(requesterInfo.getString("name")));
				response.put(invite);
			} catch (JSONException | SQLException e) {
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
