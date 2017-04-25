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
		database.executeUpdate("DELETE FROM tour_course_detail_info");
		database.executeUpdate("DELETE FROM leisure_detail_info");
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
				// 문의 및 안내

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
				// 문의 및 안내
				
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
				// 연령제한
				
				String reservationPlace = item.has("bookingplace") ? item.getString("bookingplace") : null;
				// 예매처
				
				String startDate = item.has("eventstartdate") ? String.valueOf(item.getLong("eventstartdate")) : null;
				// 행사 시작일
				
				String endDate = item.has("eventenddate") ? String.valueOf(item.getLong("eventenddate")) : null;
				// 행사 종료일
				
				String homepage = item.has("eventhomepage") ? item.getString("eventhomepage") : null;
				// 홈페이지
				
				String place = item.has("eventplace") ? item.getString("eventplace") : null;
				// 행사 장소
				
				String placeInfo = item.has("placeinfo") ? item.getString("placeinfo") : null;
				// 행사장 위치 안내
				
				String festivalGrade = item.has("festivalgrade") ? item.getString("festivalgrade") : null;
				// 축제 등급
				
				String spendTime = item.has("spendtimefestival") ? item.getString("spendtimefestival") : null;
				// 소요시간
				
				String[] sponsors = new String[2];
				// 주최자
				
				String[] sponsorsTel = new String[2];
				// 주관자
				
				sponsors[0] = item.has("sponsor1") ? item.getString("sponsor1") : null;
				sponsorsTel[0] = item.has("sponsor1_tel") ? item.getString("sponsor1_tel") : null;
				// 주최자
				
				sponsors[1] = item.has("sponsor2") ? item.getString("sponsor2") : null;
				sponsorsTel[1] = item.has("sponsor2_tel") ? item.getString("sponsor2_tel") : null;
				// 주관자
				
				String subEvent = item.has("subevent") ? item.getString("subevent") : null;
				// 부대행사
				
				String useFee = item.has("usetimefestival") ? item.getString("usetimefestival") : null;
				// 이용요금
				
				database.executeUpdate("INSERT INTO festival_detail_info VALUES(", contentId, ", '", ageLimit, "', '", reservationPlace, "', '", startDate, "', '", endDate, "', '", homepage, "', '", place, "', '", placeInfo, "' ,'", festivalGrade, "', '", spendTime, "', '", sponsors[0], "', '", sponsorsTel[0], "', '", sponsors[1], "', '", sponsorsTel[1], "', '", subEvent, "', '", useFee, "')");
			} else if(contentTypeId == 25) {
				// 여행코스
				JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
				
				String distance = item.has("distance") ? item.getString("distance") : null;
				// 코스 총거리
				
				String infoCenter = item.has("infocentertourcourse") ? item.getString("infocentertourcourse") : null;
				/*
				 * 문의 및 안내
				 * 현재 모두 null
				 */
				
				String schedule = item.has("schedule") ? item.getString("schedule") : null;
				// 코스 일정
				
				String spendTime = item.has("taketime") ? item.getString("taketime") : null;
				/*
				 * 코스 총 소요시간
				 * 현재 모두 null
				 */
				
				String theme = item.has("theme") ? item.getString("theme") : null;
				/*
				 * 코스 테마
				 * 현재 모두 null
				 */
				
				database.executeUpdate("INSERT INTO tour_course_detail_info VALUES(", contentId, ", '", distance, "', '", infoCenter, "', '", schedule, "', '", spendTime, "', '", theme, "')");
			} else if(contentTypeId == 28) {
				// 레포츠
				JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
				
				String accomCount = item.has("accomcountleports") ? item.getString("accomcountleports") : null;
				// 수용인원
				
				String babyCarriage = item.has("chkbabycarriageleports") ? item.getString("chkbabycarriageleports") : null;
				// 유모차 대여 여부

				String creditCard = item.has("chkcreditcardleports") ? item.getString("chkcreditcardleports") : null;
				// 신용카드 가능 여부

				String pet = item.has("chkpetleports") ? item.getString("chkpetleports") : null;
				// 애완동물 가능 여부
				
				String ageRange = item.has("expagerange") ? item.getString("expagerange") : null;
				// 체험가능 연령
				
				String infoCenter = item.has("infocenterleports") ? item.getString("infocenterleports") : null;
				// 문의 및 안내
				
				String openPeriod = item.has("openperiod") ? item.getString("openperiod") : null;
				// 개장기간
				
				String parking = item.has("parkingleports") ? item.getString("parkingleports") : null;
				// 주차시설
				
				String parkingFee = item.has("parkingfeeleports") ? item.getString("parkingfeeleports") : null;
				// 주차요금
				
				String reservation = item.has("reservation") ? item.getString("reservation") : null;
				// 예약안내
				
				String restDate = item.has("restdateleports") ? item.getString("restdateleports") : null;
				// 쉬는날
				
				String scale = item.has("scaleleports") ? item.getString("scaleleports") : null;
				// 규모
				
				String useFee = item.has("usefeeleports") ? item.getString("usefeeleports") : null;
				// 입장료
				
				String useTime = item.has("usetimeleports") ? item.getString("usetimeleports") : null;
				// 이용시간
				
				database.executeUpdate("INSERT INTO leisure_detail_info VALUES(", contentId, ", '", accomCount, "', '", babyCarriage, "', '", creditCard, "', '", pet, "', '", ageRange, "', '", infoCenter, "', '", openPeriod, "', '", parking, "', '", parkingFee, "', '", reservation, "', '", restDate, "', '", scale, "', '", useFee, "', '", useTime, "')");
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
