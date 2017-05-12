package com.planb.restful.attractions.details;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.DataBase;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/attractions/detail/:content_id", method = HttpMethod.GET)
public class DetailInfo implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		DataBase database = DataBase.getInstance();
		JSONObject response = new JSONObject();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		int contentId = Integer.parseInt(ctx.request().getParam("content_id"));
		
		ResultSet contentInfo = database.executeQuery("SELECT * FROM attractions_basic WHERE content_id=", contentId);
		int contentTypeId = 0;
		try {
			contentInfo.next();
			contentTypeId = contentInfo.getInt("contentTypeId");
			response.put("content_id", contentInfo.getInt("content_id"));
			response.put("image", contentInfo.getString("image_big_url") == null ? "정보 없음" : contentInfo.getString("image_big_url"));
			response.put("title", contentInfo.getString("title"));
			response.put("category", contentInfo.getString("cat3") == null ? "정보 없음" : contentInfo.getString("cat3"));
			response.put("address", contentInfo.getString("address") == null ? "정보 없음" : contentInfo.getString("address"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet contentCommonInfo;
		ResultSet contentDetailInfo;
		ResultSet contentImage;
		try {
			switch(contentTypeId) {
			case 12:
				// 관광지
				contentDetailInfo = database.executeQuery("SELECT * FROM tourrism_detail_info WHERE content_id=", contentId);
				contentDetailInfo.next();
				response.put("credit_card", contentDetailInfo.getString("credit_card") == null ? "없음" : contentDetailInfo.getString("credit_card"));
				response.put("baby_carriage", contentDetailInfo.getString("baby_carriage") == null ? "없음" : contentDetailInfo.getString("baby_carriage"));
				response.put("pet", contentDetailInfo.getString("pet") == null ? "없음" : contentDetailInfo.getString("pet"));
				response.put("info_center", extractPhoneNumber(contentDetailInfo.getString("info_center")));
				response.put("use_time", contentDetailInfo.getString("use_time") == null ? "정보 없음" : contentDetailInfo.getString("use_time"));
				response.put("rest_date", contentDetailInfo.getString("rest_date") == null ? "정보 없음" : contentDetailInfo.getString("rest_date"));
				break;
			case 14:
				// 문화시설
				contentDetailInfo = database.executeQuery("SELECT * FROM cultural_facility_detail_info WHERE content_id=", contentId);
				contentDetailInfo.next();
				response.put("credit_card", contentDetailInfo.getString("credit_card") == null ? "없음" : contentDetailInfo.getString("credit_card"));
				response.put("baby_carriage", contentDetailInfo.getString("baby_carriage") == null ? "없음" : contentDetailInfo.getString("baby_carriage"));
				response.put("pet", contentDetailInfo.getString("pet") == null ? "없음" : contentDetailInfo.getString("pet"));
				response.put("info_center", extractPhoneNumber(contentDetailInfo.getString("info_center")));
				response.put("use_time", contentDetailInfo.getString("use_time") == null ? "정보 없음" : contentDetailInfo.getString("use_time"));
				response.put("rest_date", contentDetailInfo.getString("rest_date") == null ? "정보 없음" : contentDetailInfo.getString("rest_date"));
				response.put("use_fee", contentDetailInfo.getString("use_fee") == "" ? "정보 없음" : contentDetailInfo.getString("use_fee"));
				response.put("spend_time", contentDetailInfo.getString("spend_time") == null ? "정보 없음" : contentDetailInfo.getString("spend_time"));
				break;
			case 15:
				// 축제, 공연, 행사
				contentCommonInfo = database.executeQuery("SELECT * FROM attractions_detail_common WHERE content_id=", contentId);
				contentCommonInfo.next();
				response.put("info_center", extractPhoneNumber(contentCommonInfo.getString("tel")));
				contentDetailInfo = database.executeQuery("SELECT * FROM festival_detail_info WHERE content_id=", contentId);
				contentDetailInfo.next();
				response.put("start_date", contentDetailInfo.getString("start_date"));
				response.put("end_date", contentDetailInfo.getString("end_date"));
				response.put("use_fee", contentDetailInfo.getString("use_fee") == "" ? "정보 없음" : contentDetailInfo.getString("use_fee"));
				response.put("spend_time", contentDetailInfo.getString("spend_time") == null ? "정보 없음" : contentDetailInfo.getString("spend_time"));
				break;
			case 25:
				// 여행코스
				contentDetailInfo = database.executeQuery("SELECT * FROM tour_course_detail_info WHERE content_id=", contentId);
				contentDetailInfo.next();
				response.put("spend_time", contentDetailInfo.getString("spend_time"));
				response.put("distance", contentDetailInfo.getString("distance"));
				break;
			case 28:
				// 레포츠
				contentDetailInfo = database.executeQuery("SELECT * FROM leports_detail_info WHERE content_id=", contentId);
				contentDetailInfo.next();
				response.put("credit_card", contentDetailInfo.getString("credit_card") == null ? "없음" : contentDetailInfo.getString("credit_card"));
				response.put("baby_carriage", contentDetailInfo.getString("baby_carriage") == null ? "없음" : contentDetailInfo.getString("baby_carriage"));
				response.put("pet", contentDetailInfo.getString("pet") == null ? "없음" : contentDetailInfo.getString("pet"));
				response.put("info_center", extractPhoneNumber(contentDetailInfo.getString("info_center")));
				response.put("use_time", contentDetailInfo.getString("use_time") == null ? "정보 없음" : contentDetailInfo.getString("use_time"));
				response.put("rest_date", contentDetailInfo.getString("rest_date") == null ? "정보 없음" : contentDetailInfo.getString("rest_date"));
				response.put("use_fee", contentDetailInfo.getString("use_fee") == "" ? "정보 없음" : contentDetailInfo.getString("use_fee"));
				break;
			case 32:
				// 숙박
				contentDetailInfo = database.executeQuery("SELECT * FROM accommodation_detail_info WHERE content_id=", contentId);
				contentDetailInfo.next();
				break;
			case 38:
				// 쇼핑
				contentDetailInfo = database.executeQuery("SELECT * FROM shopping_detail_info WHERE content_id=", contentId);
				contentDetailInfo.next();
				response.put("credit_card", contentDetailInfo.getString("credit_card") == null ? "없음" : contentDetailInfo.getString("credit_card"));
				response.put("baby_carriage", contentDetailInfo.getString("baby_carriage") == null ? "없음" : contentDetailInfo.getString("baby_carriage"));
				response.put("pet", contentDetailInfo.getString("pet") == null ? "없음" : contentDetailInfo.getString("pet"));
				response.put("info_center", extractPhoneNumber(contentDetailInfo.getString("info_center")));
				break;
			case 39:
				// 식당
				contentDetailInfo = database.executeQuery("SELECT * FROM restaurant_detail_info WHERE content_id=", contentId);
				contentDetailInfo.next();
				break;
			default:
				break;
		}
		ResultSet wishInfo = database.executeQuery("SELECT * FROM wish_list WHERE client_id='", clientId, "' AND content_id=", contentId);
		if(wishInfo.next()) {
			response.put("wish", true);
		}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		// 이미지 추가
	}
	
	private static String extractPhoneNumber(String phoneNumber) {
		if(phoneNumber == null) {
			return "정보 없음";
		}
		Pattern p = Pattern.compile("\\d+-\\d+-\\d+");
		Matcher m = p.matcher(phoneNumber);
		if(m.find()) {
			return m.group().toString();
		} else {
			return "정보 없음";
		}
	}
}
