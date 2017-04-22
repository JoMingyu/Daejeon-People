package com.planb.tourapi.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.support.database.DataBase;
import com.planb.tourapi.support.Params;
import com.planb.tourapi.support.Request;

public class AreaBasedTourListParser {
	/*
	 * 지역기반 관광정보 리스트 조회
	 * JsonArray를 순차 탐색하며 DB 저장
	 */
	private static String URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" + Params.defaultAppendParams;
	private static DataBase database = DataBase.getInstance();
	
	public static void parse() {
		int totalCount = Request.getTotalCount(URL);
		URL = URL + "&numOfRows=" + totalCount;
		JSONArray itemsArray = Request.getItems(URL);
		for(int i = 0; i < itemsArray.length(); i++) {
			JSONObject row = itemsArray.getJSONObject(i);
			
			int contentId = row.getInt("contentid");
			int contentTypeId = row.getInt("contenttypeid");
			String title = row.getString("title").replace("'", "''");
			String cat1 = row.getString("cat1");
			String cat2 = row.getString("cat2");
			String cat3 = null;
			if(row.has("cat3")) {
				cat3 = row.getString("cat3");
			}
			// 336개 여행지 중 하나가 소분류가 없음
			
			String address = null;
			if(contentTypeId != 25) {
				address = row.getString("addr1");
			}
			// 여행코스의 경우 주소가 없음
			
			double mapX = 0.0;
			if(row.has("mapx")) {
				mapX = row.getDouble("mapx");
			}
			double mapY = 0.0;
			if(row.has("mapy")) {
				mapY = row.getDouble("mapy");
			}
			// 336개 여행지 중 3개가 좌표가 없음
			
			int readCount = row.getInt("readcount");
			String createdTime = String.valueOf(row.getLong("createdtime"));
			String lastModifiedTime = String.valueOf(row.getLong("modifiedtime"));
			String tel = null;
			if(row.has("tel")) {
				tel = row.getString("tel");
			}
			// 전화번호 없는 경우 존재
			
			String imageMiniUrl = null;
			if(row.has("firstimage2")) {
				imageMiniUrl = row.getString("firstimage2");
			}
			String imageBigUrl = null;
			if(row.has("firstimage1")) {
				imageBigUrl = row.getString("firstimage1");
			}
			// 대표이미지가 없는 경우 존재
			
			database.executeUpdate("DELETE FROM sttractions_basic");
			database.executeUpdate("INSERT INTO attractions_basic VALUES(", contentId, ", ", contentTypeId, ", '", title, "', '", cat1, "', '", cat2, "', '", cat3, "', '", address, "', ", mapX, ", ", mapY, ", ", readCount, ", '", createdTime, "', '", lastModifiedTime, "', '", tel, "', '", imageMiniUrl, "', '", imageBigUrl, "')");
		}
	}
}
