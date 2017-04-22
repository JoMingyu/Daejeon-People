package com.planb.tourapi.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.planb.support.database.DataBase;
import com.planb.tourapi.support.Params;
import com.planb.tourapi.support.Request;

public class DetailInfoParser {
	private static String URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro" + Params.defaultAppendParams;
	private static DataBase database = DataBase.getInstance();
	
	private static void clearTables() {
		database.executeUpdate("DELETE FROM tourrism_detail_info");
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
		try {
			while(rs.next()) {
				int contentId = rs.getInt("content_id");
				int contentTypeId = rs.getInt("content_type_id");
				JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
				if(contentTypeId == 12) {
					// 12 : 관광지
//					JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
					
					String accomcount = null;
					if(item.has("accomcount")) {
						accomcount = item.getString("accomcount");
					}
					// 수용인원. 문자열 형태로, 정보가 없는 경우가 많음
					
					String babyCarriage = null;
					if(item.has("chkbabycarriage")) {
						item.getString("chkbabycarriage");
					}
					// 유모차 대여 여부
					
					String creditCard = null;
					if(item.has("chkcreditcard")) {
						item.getString("chkcreditcard");
					}
					// 신용카드 가능 여부
					
					String pet = null;
					if(item.has("chkpet")) {
						item.getString("chkpet");
					}
					// 애완동물 가능 여부
					
					String ageRange = null;
					if(item.has("expagerange")) {
						ageRange = item.getString("expagerange");
					}
					// 체험가능 연령
					
					String guide = item.getString("expguide");
					// 가이드
					
					boolean culturalHeritage = false;
					boolean naturalHeritage = false;
					boolean archivalHeritage = false;
					if(item.has("heritage1") && item.has("heritage2") && item.has("heritage3")) {
						int heritage1 = item.getInt("heritage1");
						int heritage2 = item.getInt("heritage2");
						int heritage3 = item.getInt("heritage3");
						culturalHeritage = (heritage1 == 1) ? true : false; 
						naturalHeritage = (heritage2 == 1) ? true : false; 
						archivalHeritage = (heritage3 == 1) ? true : false; 
						// boolean이지만 값은 0 또는 1, 따라서 삼항으로 타입 변환
					}
					// 문화재 여부
					
					String infoCenter = null;
					if(item.has("infocenter")) {
						infoCenter = item.getString("infocenter");
					}
					// 문의처
					
					String openDate = null;
					if(item.has("opendate")) {
						openDate = item.getString("opendate");
					}
					// 개장일
					
					String restDate = null;
					if(item.has("restdate")) {
						restDate = item.getString("restdate");
					}
					// 쉬는날
					
					String parking = null;
					if(item.has("parking")) {
						parking = item.getString("parking");
					}
					// 주차시설
					
					String useSeason = null;
					if(item.has("useseason")) {
						useSeason = item.getString("useseason");
					}
					// 이용시기
					
					String useTime = null;
					if(item.has("usetime")) {
						useTime = item.getString("usetime");
					}
					// 이용시간
					
					System.out.println(item);
				}
				
				database.executeUpdate("INSERT INTO tourrism_detail_info VALUES(");
//				JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
			}
			System.out.println("Detail Info Parse Success.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
