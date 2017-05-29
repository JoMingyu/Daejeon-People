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
		JSONObject response = new JSONObject();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		int contentId = Integer.parseInt(ctx.request().getParam("content_id"));
		
		ResultSet contentInfo = DataBase.executeQuery("SELECT * FROM attractions_basic WHERE content_id=?", contentId);
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
		ResultSet wishInfo;
		ResultSet contentImage;
		String baseQuery = "SELECT * FROM ? WHERE content_id=?";
		try {
			switch(contentTypeId) {
			case 12:
				// 관광지
				contentDetailInfo = DataBase.executeQuery(baseQuery, "tourrism_detail_info", contentId);
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
				contentDetailInfo = DataBase.executeQuery(baseQuery, "cultural_facility_detail_info", contentId);
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
				contentCommonInfo = DataBase.executeQuery(baseQuery, "attractions_detail_common", contentId);
				contentCommonInfo.next();
				response.put("info_center", extractPhoneNumber(contentCommonInfo.getString("tel")));

				contentDetailInfo = DataBase.executeQuery(baseQuery, "festival_detail_info", contentId);
				contentDetailInfo.next();
				response.put("start_date", contentDetailInfo.getString("start_date"));
				response.put("end_date", contentDetailInfo.getString("end_date"));
				response.put("use_fee", contentDetailInfo.getString("use_fee") == "" ? "정보 없음" : contentDetailInfo.getString("use_fee"));
				response.put("spend_time", contentDetailInfo.getString("spend_time") == null ? "정보 없음" : contentDetailInfo.getString("spend_time"));
				break;
			case 25:
				// 여행코스
				contentDetailInfo = DataBase.executeQuery(baseQuery, "tour_course_detail_info", contentId);
				contentDetailInfo.next();
				response.put("spend_time", contentDetailInfo.getString("spend_time"));
				response.put("distance", contentDetailInfo.getString("distance"));
				break;
			case 28:
				// 레포츠
				contentDetailInfo = DataBase.executeQuery(baseQuery, "leports_detail_info", contentId);
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
				contentDetailInfo = DataBase.executeQuery(baseQuery, "accommodation_detail_info", contentId);
				contentDetailInfo.next();
				response.put("info_center", extractPhoneNumber(contentDetailInfo.getString("info_center")));
				response.put("checkin_time", contentDetailInfo.getString("checkin_time"));
				response.put("checkout_time", contentDetailInfo.getString("checkout_time"));
				response.put("benikia", contentDetailInfo.getBoolean("benikia"));
				response.put("goodstay", contentDetailInfo.getBoolean("goodstay"));
				response.put("accomcount", contentDetailInfo.getString("accomcount") == null ? "정보 없음" : contentDetailInfo.getString("accomcount"));
				break;
			case 38:
				// 쇼핑
				contentDetailInfo = DataBase.executeQuery("SELECT * FROM shopping_detail_info WHERE content_id=", contentId);
				contentDetailInfo.next();
				response.put("credit_card", contentDetailInfo.getString("credit_card") == null ? "없음" : contentDetailInfo.getString("credit_card"));
				response.put("baby_carriage", contentDetailInfo.getString("baby_carriage") == null ? "없음" : contentDetailInfo.getString("baby_carriage"));
				response.put("pet", contentDetailInfo.getString("pet") == null ? "없음" : contentDetailInfo.getString("pet"));
				response.put("info_center", extractPhoneNumber(contentDetailInfo.getString("info_center")));
				response.put("use_time", contentDetailInfo.getString("use_time") == null ? "정보 없음" : contentDetailInfo.getString("use_time"));
				response.put("rest_date", contentDetailInfo.getString("rest_date") == null ? "정보 없음" : contentDetailInfo.getString("rest_date"));
				break;
			case 39:
				// 식당
				contentDetailInfo = DataBase.executeQuery(baseQuery, "restaurant_detail_info", contentId);
				contentDetailInfo.next();
				response.put("credit_card", contentDetailInfo.getString("credit_card") == null ? "없음" : contentDetailInfo.getString("credit_card"));
				response.put("info_center", extractPhoneNumber(contentDetailInfo.getString("info_center")));
				response.put("use_time", contentDetailInfo.getString("use_time") == null ? "정보 없음" : contentDetailInfo.getString("use_time"));
				response.put("rest_date", contentDetailInfo.getString("rest_date") == null ? "정보 없음" : contentDetailInfo.getString("rest_date"));
				response.put("rep_menu", contentDetailInfo.getString("rep_menu") == null ? "정보 없음" : contentDetailInfo.getString("rep_menu"));
				break;
			default:
				break;
		}
		wishInfo = DataBase.executeQuery("SELECT * FROM wish_list WHERE client_id=? AND content_id=?", clientId, contentId);
		if(wishInfo.next()) {
			response.put("wish", true);
		}
		
		contentImage = DataBase.executeQuery("SELECT * FROM attractions_images WHERE content_id=?", contentId);
		int count = 0;
		if(contentImage.next()) {
			response.put("additional_image", true);
			do {
				response.put("additional_image_" + ++count, contentImage.getString("image"));
			} while(contentImage.next());
		}
		} catch(SQLException e) {
			e.printStackTrace();
		}
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
