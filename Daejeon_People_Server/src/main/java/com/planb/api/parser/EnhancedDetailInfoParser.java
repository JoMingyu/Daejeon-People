package com.planb.api.parser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import com.planb.api.support.Params;
import com.planb.api.support.HttpRequestForParser;
import com.planb.support.utilities.DataBase;

public class EnhancedDetailInfoParser {
	private static String URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro" + Params.defaultAppendParams;
	private static DataBase database = DataBase.getInstance();
	
	private static void clearTables() {
		database.executeUpdate("DELETE FROM accommodation_detail_info");
		database.executeUpdate("DELETE FROM cultural_facility_detail_info");
		database.executeUpdate("DELETE FROM festival_detail_info");
		database.executeUpdate("DELETE FROM leports_detail_info");
		database.executeUpdate("DELETE FROM restaurant_detail_info");
		database.executeUpdate("DELETE FROM shopping_detail_info");
		database.executeUpdate("DELETE FROM tour_course_detail_info");
		database.executeUpdate("DELETE FROM tourrism_detail_info");
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
		Pattern p = Pattern.compile("\\d+");
		
		try {
			while(rs.next()) {
				contentInfoMap.put(rs.getInt("content_id"), rs.getInt("content_type_id"));
			}
			// To escape SQLException : Operation not allowed after ResultSet closed
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(int contentId : contentInfoMap.keySet()) {
			int contentTypeId = contentInfoMap.get(contentId);
			JSONObject item = HttpRequestForParser.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
			if (contentTypeId == 12) {
				// 관광지
				
				String creditCard = item.has("chkcreditcard") ? item.getString("chkcreditcard") : null;
				// 신용카드 가능 여부
				
				String babyCarriage = item.has("chkbabycarriage") ? item.getString("chkbabycarriage") : null;
				// 유모차 대여 여부

				String pet = item.has("chkpet") ? item.getString("chkpet") : null;
				// 애완동물 가능 여부
				
				String infoCenter = item.has("infocenter") ? item.getString("infocenter").replace("'", "''") : null;
				// 문의 및 안내
				
				String useTime = item.has("usetime") ? item.getString("usetime") : null;
				// 이용시간
				
				String restDate = item.has("restdate") ? item.getString("restdate") : null;
				// 쉬는날
				
//				String accomCount = item.has("accomcount") ? item.getString("accomcount") : null;
				// 수용인원. 문자열 형태로, 정보가 없는 경우가 많음

//				String ageRange = item.has("expagerange") ? item.getString("expagerange") : null;
				// 체험가능 연령

//				String guide = item.has("expguide") ? item.getString("expguide") : null;
				// 가이드

//				int heritage1 = item.has("heritage1") ? item.getInt("heritage1") : 0;
//				int heritage2 = item.has("heritage2") ? item.getInt("heritage2") : 0;
//				int heritage3 = item.has("heritage3") ? item.getInt("heritage3") : 0;
//				boolean culturalHeritage = heritage1 == 1 ? true : false;
//				boolean naturalHeritage = heritage2 == 1 ? true : false;
//				boolean archivalHeritage = heritage3 == 1 ? true : false;
				/*
				 *  문화재 여부
				 *  boolean이지만 값은 0 또는 1, 따라서 타입 변환
				 */

//				String openDate = item.has("opendate") ? item.getString("opendate") : null;
				// 개장일

//				String parking = item.has("parking") ? item.getString("parking") : null;
				// 주차시설

//				String useSeason = item.has("useseason") ? item.getString("useseason") : null;
				// 이용시기

				database.executeUpdate("INSERT INTO tourrism_detail_info VALUES(", contentId, ", '", creditCard, "', '", babyCarriage, "', '", pet, "', '", infoCenter, "', '", useTime, "', '", restDate, "')");
			} else if(contentTypeId == 14) {
				// 문화시설
				
				String creditCard = item.has("chkcreditcardculture") ? item.getString("chkcreditcardculture") : null;
				// 신용카드 가능 여부
				
				String babyCarriage = item.has("chkbabycarriageculture") ? item.getString("chkbabycarriageculture") : null;
				// 유모차 대여 여부

				String pet = item.has("chkpetculture") ? item.getString("chkpetculture") : null;
				// 애완동물 가능 여부
				
				String infoCenter = item.has("infocenterculture") ? item.getString("infocenterculture") : null;
				// 문의 및 안내
				
				String useTime = item.has("usetimeculture") ? item.getString("usetimeculture") : null;
				// 이용시간
				
				String restDate = item.has("restdateculture") ? item.getString("restdateculture") : null;
				// 쉬는날
				
				String useFee = item.has("usefee") ? item.getString("usefee").replace("'", "''") : null;
				// 이용요금
				
				String spendTime = item.has("spendtime") ? item.getString("spendtime") : null;
				// 관람 소요시간
				
//				String accomCount = item.has("accomcountculture") ? item.getString("accomcountculture") : null;
				// 수용인원. 문자열 형태로, 정보가 없는 경우가 많음
				
//				String discount = item.has("discountinfo") ? item.getString("discountinfo") : null;
				/*
				 * 할인정보
				 * 모두 null
				 */
				
//				String parking = item.has("parkingculture") ? item.getString("parkingculture") : null;
				// 주차시설
				
//				String parkingFee = item.has("parkingfee") ? item.getString("parkingfee") : null;
				// 주차요금
				
//				String scale = item.has("scale") ? item.getString("scale") : null;
				// 규모
				
				database.executeUpdate("INSERT INTO cultural_facility_detail_info VALUES(", contentId, ", '", creditCard, "', '", babyCarriage, "', '", pet, "', '", infoCenter, "', '", useTime, "', '", restDate, "', '", useFee, "', '", spendTime, "')");
			} else if(contentTypeId == 15) {
				// 축제, 공연, 행사
				
				String startDate = item.has("eventstartdate") ? String.valueOf(item.getLong("eventstartdate")) : null;
				// 행사 시작일
				
				String endDate = item.has("eventenddate") ? String.valueOf(item.getLong("eventenddate")) : null;
				// 행사 종료일
				
				String useFee = item.has("usetimefestival") ? item.getString("usetimefestival") : null;
				// 이용요금
				
				String spendTime = item.has("spendtimefestival") ? item.getString("spendtimefestival") : null;
				// 소요시간
				
				String place = item.has("eventplace") ? item.getString("eventplace") : null;
				// 행사 장소
				
//				String ageLimit = item.has("agelimit") ? item.getString("agelimit") : null;
				// 연령제한
				
//				String reservationPlace = item.has("bookingplace") ? item.getString("bookingplace") : null;
				// 예매처
				
//				String homepage = item.has("eventhomepage") ? item.getString("eventhomepage") : null;
				// 홈페이지
				
//				String placeInfo = item.has("placeinfo") ? item.getString("placeinfo") : null;
				// 행사장 위치 안내
				
//				String festivalGrade = item.has("festivalgrade") ? item.getString("festivalgrade") : null;
				// 축제 등급
				
//				String[] sponsors = new String[2];
				// 주최자
				
//				String[] sponsorsTel = new String[2];
				// 주관자
				
//				sponsors[0] = item.has("sponsor1") ? item.getString("sponsor1") : null;
//				sponsorsTel[0] = item.has("sponsor1_tel") ? item.getString("sponsor1_tel") : null;
				// 주최자
				
//				sponsors[1] = item.has("sponsor2") ? item.getString("sponsor2") : null;
//				sponsorsTel[1] = item.has("sponsor2_tel") ? item.getString("sponsor2_tel") : null;
				// 주관자
				
//				String subEvent = item.has("subevent") ? item.getString("subevent") : null;
				// 부대행사
				
				database.executeUpdate("INSERT INTO festival_detail_info VALUES(", contentId, ", '", startDate, "', '", endDate, "', '", useFee, "', '", spendTime, "', '", place, "')");
			} else if(contentTypeId == 25) {
				// 여행코스
				
				String spendTime = item.has("taketime") ? item.getString("taketime") : null;
				// 코스 총 소요시간
				
				String distance = item.has("distance") ? item.getString("distance") : null;
				// 코스 총거리
				
//				String infoCenter = item.has("infocentertourcourse") ? item.getString("infocentertourcourse") : null;
				/*
				 * 문의 및 안내
				 * 현재 모두 null
				 */
				
//				String schedule = item.has("schedule") ? item.getString("schedule") : null;
				/*
				 * 코스 일정
				 * 현재 모두 null
				 */
				
//				String theme = item.has("theme") ? item.getString("theme") : null;
				/*
				 * 코스 테마
				 * 현재 모두 null
				 */
				
				database.executeUpdate("INSERT INTO tour_course_detail_info VALUES(", contentId, ", '", spendTime, "', '", distance, "')");
			} else if(contentTypeId == 28) {
				// 레포츠
				
				String creditCard = item.has("chkcreditcardleports") ? item.getString("chkcreditcardleports") : null;
				// 신용카드 가능 여부
				
				String babyCarriage = item.has("chkbabycarriageleports") ? item.getString("chkbabycarriageleports") : null;
				// 유모차 대여 여부

				String pet = item.has("chkpetleports") ? item.getString("chkpetleports") : null;
				// 애완동물 가능 여부
				
				String infoCenter = item.has("infocenterleports") ? item.getString("infocenterleports") : null;
				// 문의 및 안내
				
				String useTime = item.has("usetimeleports") ? item.getString("usetimeleports") : null;
				// 이용시간
				
				String restDate = item.has("restdateleports") ? item.getString("restdateleports") : null;
				// 쉬는날
				
				String useFee = item.has("usefeeleports") ? item.getString("usefeeleports") : null;
				// 입장료
				
//				String accomCount = item.has("accomcountleports") ? item.getString("accomcountleports") : null;
				// 수용인원
				
//				String ageRange = item.has("expagerangeleports") ? item.getString("expagerangeleports") : null;
				// 체험가능 연령
				
//				String openPeriod = item.has("openperiod") ? item.getString("openperiod") : null;
				/*
				 * 개장기간
				 * 모두 null
				 */
				
//				String parking = item.has("parkingleports") ? item.getString("parkingleports") : null;
				// 주차시설
				
//				String parkingFee = item.has("parkingfeeleports") ? item.getString("parkingfeeleports") : null;
				// 주차요금
				
//				String reservation = item.has("reservation") ? item.getString("reservation") : null;
				// 예약안내
				
//				String scale = item.has("scaleleports") ? item.getString("scaleleports") : null;
				// 규모
				
				database.executeUpdate("INSERT INTO leports_detail_info VALUES(", contentId, ", '", creditCard, "', '", babyCarriage, "', '", pet, "', '", infoCenter, "', '", useTime, "', '", restDate, "', '", useFee, "')");
			} else if(contentTypeId == 32) {
				// 숙박
				
				String accomCount = item.has("accomcountlodging") ? item.getString("accomcountlodging") : null;
				// 수용인원
				
				int benikiaInt = item.has("benikia") ? item.getInt("benikia") : null;
				boolean benikia = benikiaInt == 1 ? true : false;
				// 베니키아 여부
				
				int goodStayInt = item.has("goodstay") ? item.getInt("goodstay") : null;
				boolean goodStay = goodStayInt == 1 ? true : false;
				// 굿스테이 여부
				
				int koreanHouseInt = item.has("hanok") ? item.getInt("hanok") : null;
				boolean koreanHouse = koreanHouseInt == 1 ? true : false;
				// 한옥 여부
				
				int barbecueInt = item.has("barbecue") ? item.getInt("barbecue") : null;
				boolean barbecue = barbecueInt == 1 ? true : false;
				// 바비큐장 여부
				
				int beautyInt = item.has("beauty") ? item.getInt("beauty") : null;
				boolean beauty = beautyInt == 1 ? true : false;
				// 뷰티시설 정보
				
				int beverageInt = item.has("beverage") ? item.getInt("beverage") : null;
				boolean beverage = beverageInt == 1 ? true : false;
				// 식음료장 여부
				
				int bicycleInt = item.has("bicycle") ? item.getInt("bicycle") : null;
				boolean bicycle = bicycleInt == 1 ? true : false;
				// 자전거 대여 여부
				
				int campfireInt = item.has("campfire") ? item.getInt("campfire") : null;
				boolean campfire = campfireInt == 1 ? true : false;
				// 캠프파이어 여부
				
				String cookInRoom = item.has("chkcooking") ? item.getString("chkcooking") : null;
				// 객실내 취사 여부
				
				int fitnessInt = item.has("fitness") ? item.getInt("fitness") : null;
				boolean fitness = fitnessInt == 1 ? true : false;
				// 피트니스 센터 여부
				
				int karaokeInt = item.has("karaoke") ? item.getInt("karaoke") : null;
				boolean karaoke = karaokeInt == 1 ? true : false;
				// 노래방 여부
				
				int publicBathInt = item.has("publicbath") ? item.getInt("publicbath") : null;
				boolean publicBath = publicBathInt == 1 ? true : false;
				// 공용 샤워실 여부
				
				int publicPcInt = item.has("publicpc") ? item.getInt("publicpc") : null;
				boolean publicPc = publicPcInt == 1 ? true : false;
				// 공용 PC실 여부
				
				int saunaInt = item.has("sauna") ? item.getInt("sauna") : null;
				boolean sauna = saunaInt == 1 ? true : false;
				// 사우나실 여부
				
				int seminarInt = item.has("seminar") ? item.getInt("seminar") : null;
				boolean seminar = seminarInt == 1 ? true : false;
				// 세미나실 여부
				
				int sportsInt = item.has("sports") ? item.getInt("sports") : null;
				boolean sports = sportsInt == 1 ? true : false;
				// 스포츠 시설 여부
				
				String subFacility = item.has("subfacility") ? item.getString("subfacility") : null;
				// 부대시설
				
				String checkinTime = item.has("checkintime") ? item.getString("checkintime") : null;
				// 입실 시간
				
				String checkoutTime = item.has("checkouttime") ? item.getString("checkouttime") : null;
				// 퇴실 시간
				
				String foodPlace = item.has("foodplace") ? item.getString("foodplace") : null;
				// 식음료장
				
				String infoCenter = item.has("infocenterlodging") ? item.getString("infocenterlodging") : null;
				// 문의 및 안내
				
				String parking = item.has("parkinglodging") ? item.getString("parkinglodging") : null;
				// 주차시설
				
				String pickup = item.has("pickup") ? item.getString("pickup") : null;
				// 픽업 서비스
				
				Object roomCountObj = item.has("roomcount") ? item.get("roomcount") : null;
				/*
				 * 객실 수
				 * 정수일 때도 있고 문자열일 때도 있음
				 */
				String roomCountType = roomCountObj.getClass().getSimpleName();
				// 타입 체크
				int roomCount = 0;
				if(!roomCountType.equals("Integer")) {
					// 정수형이 아니라면 Regex로 숫자만 추출
					Matcher m = p.matcher(roomCountObj.toString());
					m.find();
					roomCount = Integer.parseInt(m.group());
				}
				
				String reservation = item.has("reservationlodging") ? item.getString("reservationlodging") : null;
				// 예약안내
				
				String reservationUrl = item.has("reservationurl") ? item.getString("reservationurl") : null;
				// 예약안내 홈페이지
				
				String roomType = item.has("roomtype") ? item.getString("roomtype") : null;
				// 객실유형
				
				String scale = item.has("scalelodging") ? item.getString("scalelodging") : null;
				// 규모
				
				database.executeUpdate("INSERT INTO accommodation_detail_info VALUES(", contentId, ", '", accomCount, "', ", benikia, ", ", goodStay, ", ", koreanHouse, ", ", barbecue, ", ", beauty, ", ", beverage, ", ", bicycle, ", ", campfire, ", '", cookInRoom, "', ", fitness, ", ", karaoke, ", ", publicBath, ", ", publicPc, ", ", sauna, ", ", seminar, ", ", sports, ", '", subFacility, "', '", checkinTime, "', '", checkoutTime, "', '", foodPlace, "', '", infoCenter, "', '", parking, "', '", pickup, "', ", roomCount, ", '", reservation, "', '", reservationUrl, "', '", roomType, "', '", scale, "')");
			} else if(contentTypeId == 38) {
				// 쇼핑
				
				String babyCarriage = item.has("chkbabycarriageshopping") ? item.getString("chkbabycarriageshopping") : null;
				// 유모차 대여 여부
				
				String creditCard = item.has("chkcreditcardshopping") ? item.getString("chkcreditcardshopping") : null;
				// 신용카드 가능 여부
				
				String pet = item.has("chkpetshopping") ? item.getString("chkpetshopping") : null;
				// 애완동물 가능 여부
				
				String guide = item.has("shopguide") ? item.getString("shopguide") : null;
				// 매장안내
				
//				String cultureCenter = item.has("culturecenter") ? item.getString("culturecenter") : null;
				/*
				 * 문화센터 바로가기
				 * 모두 null
				 */
				
				String fairDay = item.has("fairday") ? item.getString("fairday") : null;
				// 장 서는 날, 하나 빼고 모두 null
				
				String infoCenter = item.has("infocentershopping") ? item.getString("infocentershopping") : null;
				// 문의 및 안내
				
				String openDate = item.has("opendateshopping") ? item.getString("opendateshopping") : null;
				// 개장일
				
				String openTime = item.has("opentime") ? item.getString("opentime") : null;
				// 영업시간
				
				String parking = item.has("parkingshopping") ? item.getString("parkingshopping") : null;
				// 주차시설
				
				String restDate = item.has("restdateshopping") ? item.getString("restdateshopping") : null;
				// 쉬는날
				
				String restroom = item.has("restroom") ? item.getString("restroom") : null;
				// 화장실 설명
				
				String saleItem = item.has("saleitem") ? item.getString("saleitem") : null;
				// 판매 품목
				
//				String saleItemCost = item.has("saleitemcost") ? item.getString("saleitemcost") : null;
				// 판매 품목별 가격
				
				String scale = item.has("scaleshopping") ? item.getString("scaleshopping") : null;
				// 규모
				
				database.executeUpdate("INSERT INTO shopping_detail_info VALUES(", contentId, ", '", babyCarriage, "', '", creditCard, "', '", pet, "', '", guide, "', '", fairDay, "', '", infoCenter, "', '", openDate, "', '", openTime, "', '", parking, "', '", restDate, "', '", restroom, "', '", saleItem, "', '", scale, "')");
			} else if(contentTypeId == 39) {
				// 음식
				
				String creditCard = item.has("chkcreditcardfood") ? item.getString("chkcreditcardfood") : null;
				// 신용카드 가능 여부
				
//				String discount = item.has("discountinfofood") ? item.getString("discountinfofood") : null;
				/*
				 * 할인정보
				 * 모두 null
				 */
				
				String repMenu = item.has("firstmenu") ? item.getString("firstmenu") : null;
				// 대표메뉴
				
				String infoCenter = item.has("infocenterfood") ? item.getString("infocenterfood") : null;
				// 문의 및 안내
				
				int kidsFacilityInt = item.has("kidsfacility") ? item.getInt("kidsfacility") : null;
				boolean kidsFacility = kidsFacilityInt == 1 ? true : false;
				// 어린이 놀이방
				
				String openDate = item.has("opendatefood") ? item.getString("opendatefood") : null;
				// 개업일
				
				String openTime = item.has("opentimefood") ? item.getString("opentimefood") : null;
				// 영업시간
				
				String packing = item.has("packing") ? item.getString("packing") : null;
				// 포장 가능 여부
				
				String parking = item.has("parkingfood") ? item.getString("parkingfood") : null;
				// 주차시설
				
				String reservation = item.has("reservationfood") ? item.getString("reservationfood") : null;
				// 예약안내
				
				String restDate = item.has("restdatefood") ? item.getString("restdatefood") : null;
				// 쉬는날
				
				String scale = item.has("scalefood") ? item.getString("scalefood") : null;
				// 규모
				
				String seat = item.has("seat") ? item.getString("seat") : null;
				// 좌석수
				
				String smoking = item.has("smoking") ? item.getString("smoking") : null;
				// 금연/흡연 여부
				
				String treatMenu = item.has("treatmenu") ? item.getString("treatmenu") : null;
				// 취급 메뉴
				
				database.executeUpdate("INSERT INTO restaurant_detail_info VALUES(", contentId, ", '", creditCard, "', '", repMenu, "', '", infoCenter, "', ", kidsFacility, ", '", openDate, "', '", openTime, "', '", packing, "', '", parking, "', '", reservation, "', '", restDate, "', '", scale, "', '", seat, "', '", smoking, "', '", treatMenu, "')");
			}
		}
		System.out.println("Detail Info Parse Success.");
	}
}
