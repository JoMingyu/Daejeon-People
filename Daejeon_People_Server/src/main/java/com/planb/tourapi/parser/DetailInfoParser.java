package com.planb.tourapi.parser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.planb.support.database.DataBase;
import com.planb.tourapi.support.Params;
import com.planb.tourapi.support.Request;

public class DetailInfoParser {
	private static String URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro" + Params.defaultAppendParams;
	private static DataBase database = DataBase.getInstance();
	
	private static void clearTables() {
		database.executeUpdate("DELETE FROM tourrism_detail_info");
		database.executeUpdate("DELETE FROM cultural_facility_detail_info");
		database.executeUpdate("DELETE FROM festival_detail_info");
		// 추가해야 함
	}
	
	public static void parse() {
		/*
		 *  파싱 한 번에 over 336 트래픽 발생, 주의 필요
		 *  연속 200트래픽 이후 잠시동안 응답을 하지 않음
		 *  데이터가 확실히 존재할 것이라는 보장이 없음
		 *  -> 데이터 존재 여부에 대한 확인 필요
		 */
		
		clearTables();
		
		ResultSet rs = database.executeQuery("SELECT * FROM attractions_basic");
		Map<Integer, Integer> contentInfoMap = new HashMap<Integer, Integer>();
		Set<Integer> contentIdSet = new HashSet<Integer>();
		Iterator<Integer> contentIdIterator = null;
		
		try {
			while(rs.next()) {
				contentInfoMap.put(rs.getInt("content_id"), rs.getInt("content_type_id"));
			}
			contentIdSet = contentInfoMap.keySet();
			contentIdIterator = contentIdSet.iterator();
			// To escape SQLException : Operation not allowed after ResultSet closed
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		while (contentIdIterator.hasNext()) {
			int contentId = contentIdIterator.next();
			int contentTypeId = contentInfoMap.get(contentId);
//			 JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
			if (contentTypeId == 12) {
				// 12 : 관광지
				JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);

				String accomCount = item.has("accomcount") ? item.getString("accomcount") : null;
				// 수용인원. 문자열 형태로, 정보가 없는 경우가 많음

				String babyCarriage = item.has("chkbabycarriage") ? item.getString("chkbabycarriage") : null;
				// 유모차 대여 여부

				String creditCard = item.has("chkcreditcard") ? item.getString("chkcreditcard") : null;
				// 신용카드 가능 여부

				String pet = item.has("chkpet") ? item.getString("chkpet") : null;
				// 애완동물 가능 여부

				String ageRange = item.has("expagerange") ? item.getString("expagerange") : null;
				// 체험가능 연령

				String guide = item.has("expguide") ? item.getString("expguide") : null;
				// 가이드

				int heritage1 = item.has("heritage1") ? item.getInt("heritage1") : 0;
				int heritage2 = item.has("heritage2") ? item.getInt("heritage2") : 0;
				int heritage3 = item.has("heritage3") ? item.getInt("heritage3") : 0;
				boolean culturalHeritage = (heritage1 == 1) ? true : false;
				boolean naturalHeritage = (heritage2 == 1) ? true : false;
				boolean archivalHeritage = (heritage3 == 1) ? true : false;
				/*
				 *  문화재 여부
				 *  boolean이지만 값은 0 또는 1, 따라서 타입 변환
				 */

				String infoCenter = item.has("infocenter") ? item.getString("infocenter").replace("'", "''") : null;
				// 문의처

				String openDate = item.has("opendate") ? item.getString("opendate") : null;
				// 개장일

				String restDate = item.has("restdate") ? item.getString("restdate") : null;
				// 쉬는날

				String parking = item.has("parking") ? item.getString("parking") : null;
				// 주차시설

				String useSeason = item.has("useseason") ? item.getString("useseason") : null;
				// 이용시기

				String useTime = item.has("usetime") ? item.getString("usetime") : null;
				// 이용시간

				database.executeUpdate("INSERT INTO tourrism_detail_info VALUES(", contentId, ", '", accomCount, "', '", babyCarriage, "', '", creditCard, "', '", pet, "', '", ageRange, "', '", guide, "', ", culturalHeritage, ", ", naturalHeritage, ", ", archivalHeritage, ", '", infoCenter, "', '", openDate, "', '", restDate, "', '", parking, "', '", useSeason, "', '", useTime, "')");
			} else if(contentTypeId == 14) {
				// 문화시설
				JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
				
				String accomCount = item.has("accomcountculture") ? item.getString("accomcountculture") : null;
				// 수용인원. 문자열 형태로, 정보가 없는 경우가 많음

				String babyCarriage = item.has("chkbabycarriageculture") ? item.getString("chkbabycarriageculture") : null;
				// 유모차 대여 여부

				String creditCard = item.has("chkcreditcardculture") ? item.getString("chkcreditcardculture") : null;
				// 신용카드 가능 여부

				String pet = item.has("chkpetculture") ? item.getString("chkpetculture") : null;
				// 애완동물 가능 여부
				
				String discount = item.has("discount") ? item.getString("discountinfo") : null;
				// 할인정보

				String infoCenter = item.has("infocenterculture") ? item.getString("infocenterculture") : null;
				// 문의처
				
				String parking = item.has("parkingculture") ? item.getString("parkingculture") : null;
				// 주차시설
				
				String parkingFee = item.has("parkingfee") ? item.getString("parkingfee") : null;
				// 주차요금
				
				String restDate = item.has("restdateculture") ? item.getString("restdateculture") : null;
				// 쉬는날
				
				String useFee = item.has("usefee") ? item.getString("usefee").replace("'", "''") : null;
				// 이용요금
				
				String useTime = item.has("usetimeculture") ? item.getString("usetimeculture") : null;
				// 이용시간
				
				String scale = item.has("scale") ? item.getString("scale") : null;
				// 규모
				
				String spendTime = item.has("spendtime") ? item.getString("spendtime") : null;
				// 관람 소요시간
				
				database.executeUpdate("INSERT INTO cultural_facility_detail_info VALUES(", contentId, ", '", accomCount, "', '", babyCarriage, "', '", creditCard, "', '", pet, "', '", discount, "', '", infoCenter, "', '", parking, "', '", parkingFee, "', '", restDate, "', '", useFee, "', '", useTime, "', '", scale, "', '", spendTime, "')");
			} else if(contentTypeId == 15) {
				// 축제, 공연, 행사
				JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
				
				String ageLimit = item.has("agelimit") ? item.getString("agelimit") : null;
				
				String reservationPlace = item.has("bookingplace") ? item.getString("bookingplace") : null;
				
				String startDate = item.has("eventstartdate") ? String.valueOf(item.getLong("eventstartdate")) : null;
				
				String endDate = item.has("eventenddate") ? String.valueOf(item.getLong("eventenddate")) : null;
				
				String homepage = item.has("eventhomepage") ? item.getString("eventhomepage") : null;
				
				String place = item.has("eventplace") ? item.getString("eventplace") : null;

				String placeInfo = item.has("placeinfo") ? item.getString("placeinfo") : null;
				
				String festivalGrade = item.has("festivalgrade") ? item.getString("festivalgrade") : null;
				
				String spendTime = item.has("spendtimefestival") ? item.getString("spendtimefestival") : null;
				
				String[] sponsors = new String[2];
				String[] sponsorsTel = new String[2];
				
				sponsors[0] = item.has("sponsor1") ? item.getString("sponsor1") : null;
				sponsorsTel[0] = item.has("sponsor1_tel") ? item.getString("sponsor1_tel") : null;
				sponsors[1] = item.has("sponsor2") ? item.getString("sponsor2") : null;
				sponsorsTel[1] = item.has("sponsor2_tel") ? item.getString("sponsor2_tel") : null;
				
				String subEvent = item.has("subevent") ? item.getString("subevent") : null;
				
				String useFee = item.has("usetimefestival") ? item.getString("usetimefestival") : null;
				
				database.executeUpdate("INSERT INTO festival_detail_info VALUES(", contentId, ", '", ageLimit, "', '", reservationPlace, "', '", startDate, "', '", endDate, "', '", homepage, "', '", place, "', '", placeInfo, "' ,'", festivalGrade, "', '", spendTime, "', '", sponsors[0], "', '", sponsorsTel[0], "', '", sponsors[1], "', '", sponsorsTel[1], "', '", subEvent, "', '", useFee, "')");
			} else if(contentTypeId == 25) {
				// 여행코스
			} else if(contentTypeId == 28) {
				// 레포츠
			} else if(contentTypeId == 32) {
				// 숙박
			} else if(contentTypeId == 38) {
				// 쇼핑
			} else if(contentTypeId == 39) {
				// 음식
			}
		}
		System.out.println("Detail Info Parse Success.");
	}
}
