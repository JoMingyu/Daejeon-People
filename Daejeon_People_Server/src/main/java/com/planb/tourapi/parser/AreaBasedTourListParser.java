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
		database.executeUpdate("DELETE FROM attractions_basic");
		int totalCount = Request.getTotalCount(URL);
		URL = URL + "&numOfRows=" + totalCount;
		
		JSONArray itemsArray = Request.getItems(URL);
		for(int i = 0; i < itemsArray.length(); i++) {
			JSONObject row = itemsArray.getJSONObject(i);
			
			int contentId = row.getInt("contentid");
			int contentTypeId = row.getInt("contenttypeid");
			
			String title = row.has("title") ? row.getString("title").replace("'", "''") : null;
			/*
			 * 여행지 타이틀
			 * 타이틀에 작은따옴표가 들어가 있는 경우 존재
			 */
			
			String cat1 = row.has("cat1") ? row.getString("cat1") : null;
			String cat2 = row.has("cat2") ? row.getString("cat2") : null;
			String cat3 = row.has("cat3") ? row.getString("cat3") : null;
			/*
			 * 대분류, 중분류, 소분류
			 * 336개 여행지 중 하나가 소분류가 없음(대전광역시)
			 */
			
			String address = row.has("addr1") ? row.getString("addr1") : null;
			/*
			 *  주소
			 *  여행코스의 경우 주소가 없음
			 */
			
			double mapX = row.has("mapx") ? row.getDouble("mapx") : 0.0;
			double mapY = row.has("mapy") ? row.getDouble("mapy") : 0.0;
			/*
			 * 좌표
			 * 336개 여행지 중 3개가 좌표가 없음
			 */
			
			int readCount = row.has("readcount") ? row.getInt("readcount") : 0;
			// 조회수
			
			String createdTime = row.has("createdtime") ? String.valueOf(row.getLong("createdtime")) : null;
			// 생성일
			
			String lastModifiedTime = row.has("modifiedtime") ? String.valueOf(row.getLong("modifiedtime")) : null;
			// 최근 수정일
			
			String tel = row.has("tel") ? row.getString("tel") : null;
			// 전화번호
			
			String imageMiniUrl = row.has("firstimage2") ? row.getString("firstimage2") : null;
			// 대표이미지 작은 사이즈
			
			String imageBigUrl = row.has("firstimage") ? row.getString("firstimage") : null;
			// 대표이미지 큰 사이즈
			
			database.executeUpdate("INSERT INTO attractions_basic VALUES(", contentId, ", ", contentTypeId, ", '", title, "', '", cat1, "', '", cat2, "', '", cat3, "', '", address, "', ", mapX, ", ", mapY, ", ", readCount, ", '", createdTime, "', '", lastModifiedTime, "', '", tel, "', '", imageMiniUrl, "', '", imageBigUrl, "')");
		}
		System.out.println("Area Based Tour List Parse Success.");
	}
}
