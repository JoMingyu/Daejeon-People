package com.planb.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.parser.support.BaseURLs;
import com.planb.parser.support.HttpClientForParser;
import com.planb.support.utilities.Log;
import com.planb.support.utilities.MySQL;

public class TourListParser implements Parser {
	// 지역기반 관광정보 리스트 파서
	
	private static final String defaultURL = BaseURLs.TOUR_LIST.getName();
	
	@Override
	public void parse() {
		int totalCount = HttpClientForParser.getTotalCount(defaultURL);
		// 요청 이전에 응답 전체 카운트를 먼저 얻어냄
		
		String requestURL = defaultURL + "&numOfRows=" + totalCount;
		// 응답받을 여행지 정보 갯수를 totalCount에 맞춰서 요청
		
		JSONArray items = HttpClientForParser.getItems(requestURL);
		
		for(int i = 0; i < items.length(); i++) {
			JSONObject item = items.getJSONObject(i);
			
			int contentId = item.getInt("contentid");
			int contentTypeId = item.getInt("contenttypeid");
			
			String title = item.has("title") ? item.getString("title").replace("'", "''") : null;
			/*
			 * 여행지 타이틀
			 * 타이틀에 작은따옴표가 들어가 있는 경우 존재
			 */
			
			String cat1 = item.has("cat1") ? item.getString("cat1") : null;
			String cat2 = item.has("cat2") ? item.getString("cat2") : null;
			String cat3 = item.has("cat3") ? item.getString("cat3") : null;
			/*
			 * 대분류, 중분류, 소분류
			 * 336개 여행지 중 하나가 소분류가 없음(대전광역시)
			 */
			
			String address = item.has("addr1") ? item.getString("addr1") : null;
			/*
			 *  주소
			 *  여행코스의 경우 주소가 없음
			 */
			
			double mapX = item.has("mapx") ? item.getDouble("mapx") : 0.0;
			double mapY = item.has("mapy") ? item.getDouble("mapy") : 0.0;
			/*
			 * 좌표
			 * 336개 여행지 중 3개가 좌표가 없음
			 */
			
			int readCount = item.has("readcount") ? item.getInt("readcount") : 0;
			// 조회수
			
//			String createdTime = item.has("createdtime") ? String.valueOf(item.getLong("createdtime")) : null;
			// 생성일
			
//			String lastModifiedTime = item.has("modifiedtime") ? String.valueOf(item.getLong("modifiedtime")) : null;
			// 최근 수정일
			
			String imageMiniUrl = item.has("firstimage2") ? item.getString("firstimage2") : null;
			// 대표이미지 작은 사이즈
			
			String imageBigUrl = item.has("firstimage") ? item.getString("firstimage") : null;
			// 대표이미지 큰 사이즈
			
			ResultSet rs = MySQL.executeQuery("SELECT * FROM attractions_basic WHERE content_id=?", contentId);
			try {
				if(rs.next()) {
					MySQL.executeUpdate("UPDATE attractions_basic SET title=?, cat1=?, cat2=?, cat3=?, mapx=?, mapy=?, views_count=?, image_mini_url=?, image_big_url=? WHERE content_id=?", title, cat1, cat2, cat3, mapX, mapY, readCount, imageMiniUrl, imageBigUrl, contentId);
				} else {
					MySQL.executeUpdate("INSERT INTO attractions_basic VALUES(?, ?, 0, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", contentId, contentTypeId, title, cat1, cat2, cat3, address, mapX, mapY, readCount, imageMiniUrl, imageBigUrl);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Log.info("Area Based Tour List Parse Success.");
	}
}
