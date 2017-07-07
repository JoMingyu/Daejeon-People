package com.planb.restful.attractions.details;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.planb.support.routing.API;
import com.planb.support.routing.REST;
import com.planb.support.routing.Route;
import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "여행지 정보", summary = "여행지 세부 정보 조회")
@REST(requestBody = "content_id : int", responseBody = "content_id : int, image : String, title : String, category : String, address : String, mapx : double, mapy : double, wish : boolean, additional_image : boolean, 추가 정보는 여행지 세부 정보 기준표 참고 바람.", successCode = 201)
@Route(uri = "/attractions/detail", method = HttpMethod.GET)
public class DetailInfo implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		JSONObject response = new JSONObject();
		
		String clientId = UserManager.getEncryptedIdFromSession(ctx);
		// 위시리스트 여부를 판단하기 위해
		
		int contentId = Integer.parseInt(ctx.request().getParam("content_id"));
		// 세부 정보를 확인할 content id
		
		ResultSet contentInfo = MySQL.executeQuery("SELECT * FROM attractions_basic WHERE content_id=?", contentId);
		int contentTypeId = 0;
		try {
			assert contentInfo != null;
			contentInfo.next();
			contentTypeId = contentInfo.getInt("content_type_id");
			response.put("content_id", contentId);
			response.put("image", contentInfo.getString("image_big_url") == null ? "정보 없음" : contentInfo.getString("image_big_url"));
			response.put("title", contentInfo.getString("title"));
			response.put("category", contentInfo.getString("cat3") == null ? "정보 없음" : contentInfo.getString("cat3"));
			response.put("address", contentInfo.getString("address") == null ? "정보 없음" : contentInfo.getString("address"));
			response.put("mapx", contentInfo.getDouble("mapx"));
			response.put("mapy", contentInfo.getDouble("mapy"));
			// 기본 정보
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ResultSet contentCommonInfo;
		// 공통 세부 정보
		
		ResultSet contentDetailInfo;
		// 카테고리별 세부 정보
		
		ResultSet wishInfo;
		// 위시리스트 여부
		
		ResultSet contentImage;
		// 추가 이미지
		
		String baseQuery = "SELECT * FROM ? WHERE content_id=?";
		// 기본 쿼리
		
		try {
			switch(contentTypeId) {
			case 12:
				// 관광지
				contentDetailInfo = MySQL.executeQuery(baseQuery, "tourrism_detail_info", contentId);
				assert contentDetailInfo != null;
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
				contentDetailInfo = MySQL.executeQuery(baseQuery, "cultural_facility_detail_info", contentId);
				assert contentDetailInfo != null;
				contentDetailInfo.next();
				response.put("credit_card", contentDetailInfo.getString("credit_card") == null ? "없음" : contentDetailInfo.getString("credit_card"));
				response.put("baby_carriage", contentDetailInfo.getString("baby_carriage") == null ? "없음" : contentDetailInfo.getString("baby_carriage"));
				response.put("pet", contentDetailInfo.getString("pet") == null ? "없음" : contentDetailInfo.getString("pet"));
				response.put("info_center", extractPhoneNumber(contentDetailInfo.getString("info_center")));
				response.put("use_time", contentDetailInfo.getString("use_time") == null ? "정보 없음" : contentDetailInfo.getString("use_time"));
				response.put("rest_date", contentDetailInfo.getString("rest_date") == null ? "정보 없음" : contentDetailInfo.getString("rest_date"));
				response.put("use_fee", Objects.equals(contentDetailInfo.getString("use_fee"), "") ? "정보 없음" : contentDetailInfo.getString("use_fee"));
				response.put("spend_time", contentDetailInfo.getString("spend_time") == null ? "정보 없음" : contentDetailInfo.getString("spend_time"));
				break;
			case 15:
				// 축제, 공연, 행사
				contentCommonInfo = MySQL.executeQuery(baseQuery, "attractions_detail_common", contentId);
				assert contentCommonInfo != null;
				contentCommonInfo.next();
				response.put("info_center", extractPhoneNumber(contentCommonInfo.getString("tel")));

				contentDetailInfo = MySQL.executeQuery(baseQuery, "festival_detail_info", contentId);
				assert contentDetailInfo != null;
				contentDetailInfo.next();
				response.put("start_date", contentDetailInfo.getString("start_date"));
				response.put("end_date", contentDetailInfo.getString("end_date"));
				response.put("use_fee", Objects.equals(contentDetailInfo.getString("use_fee"), "") ? "정보 없음" : contentDetailInfo.getString("use_fee"));
				response.put("spend_time", contentDetailInfo.getString("spend_time") == null ? "정보 없음" : contentDetailInfo.getString("spend_time"));
				break;
			case 25:
				// 여행코스
				contentDetailInfo = MySQL.executeQuery(baseQuery, "tour_course_detail_info", contentId);
				assert contentDetailInfo != null;
				contentDetailInfo.next();
				response.put("spend_time", contentDetailInfo.getString("spend_time"));
				response.put("distance", contentDetailInfo.getString("distance"));
				break;
			case 28:
				// 레포츠
				contentDetailInfo = MySQL.executeQuery(baseQuery, "leports_detail_info", contentId);
				assert contentDetailInfo != null;
				contentDetailInfo.next();
				response.put("credit_card", contentDetailInfo.getString("credit_card") == null ? "없음" : contentDetailInfo.getString("credit_card"));
				response.put("baby_carriage", contentDetailInfo.getString("baby_carriage") == null ? "없음" : contentDetailInfo.getString("baby_carriage"));
				response.put("pet", contentDetailInfo.getString("pet") == null ? "없음" : contentDetailInfo.getString("pet"));
				response.put("info_center", extractPhoneNumber(contentDetailInfo.getString("info_center")));
				response.put("use_time", contentDetailInfo.getString("use_time") == null ? "정보 없음" : contentDetailInfo.getString("use_time"));
				response.put("rest_date", contentDetailInfo.getString("rest_date") == null ? "정보 없음" : contentDetailInfo.getString("rest_date"));
				response.put("use_fee", Objects.equals(contentDetailInfo.getString("use_fee"), "") ? "정보 없음" : contentDetailInfo.getString("use_fee"));
				break;
			case 32:
				// 숙박
				contentDetailInfo = MySQL.executeQuery(baseQuery, "accommodation_detail_info", contentId);
				assert contentDetailInfo != null;
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
				contentDetailInfo = MySQL.executeQuery("SELECT * FROM shopping_detail_info WHERE content_id=", contentId);
				assert contentDetailInfo != null;
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
				contentDetailInfo = MySQL.executeQuery(baseQuery, "restaurant_detail_info", contentId);
				assert contentDetailInfo != null;
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
		
		wishInfo = MySQL.executeQuery("SELECT * FROM wish_list WHERE client_id=? AND content_id=?", clientId, contentId);
		if(wishInfo != null ? wishInfo.next() : false) {
			response.put("wish", true);
		}
		
		contentImage = MySQL.executeQuery("SELECT * FROM attractions_images WHERE content_id=?", contentId);
		int count = 0;
		if(contentImage != null ? contentImage.next() : false) {
			response.put("additional_image", true);
			do {
				response.put("additional_image_" + ++count, contentImage.getString("image"));
				// additional_image_1, additional_image_2, ...
			} while(contentImage.next());
		}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		ctx.response().setStatusCode(200);
		ctx.response().end(response.toString());
		ctx.response().close();
	}
	
	private static String extractPhoneNumber(String phoneNumber) {
		// RegEx를 통해 xxx-xxxx-xxxx 형식의 전화번호만 추출
		
		if(phoneNumber == null) {
			return "정보 없음";
		}
		Pattern p = Pattern.compile("\\d+-\\d+-\\d+");
		Matcher m = p.matcher(phoneNumber);
		if(m.find()) {
			return m.group();
		} else {
			return "정보 없음";
		}
	}
}
