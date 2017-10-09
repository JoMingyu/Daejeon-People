package com.planb.support.restful.attractions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.user.UserManager;
import com.planb.support.utilities.MySQL;

import io.vertx.ext.web.RoutingContext;

public class AttractionsListInquiry {
	private static final int numOfRows = AttractionsConfig.NUM_OF_ROWS;
	
	public static JSONArray inquireBasedKeyword(RoutingContext ctx, String keyword) {
		// 검색 + 정렬
		int sortType = Integer.parseInt(ctx.request().getParam("sort_type"));
		int page = Integer.parseInt(ctx.request().getParam("page"));
		
		String query = "SELECT * FROM attractions_basic WHERE title LIKE '%" + keyword + "%' ORDER BY ? LIMIT " + (page - 1) * numOfRows + ", " + page * numOfRows;

		ResultSet rs = null;
		switch (sortType) {
		case 1:
			// 조회순
			rs = MySQL.executeQuery(query.replaceFirst("\\?", "views_count DESC"));
			break;
		case 2:
			// 위시리스트 많은 순
			rs = MySQL.executeQuery(query.replaceFirst("\\?", "wish_count DESC"));
			break;
		case 3:
			// 거리순
			double x = Double.parseDouble(ctx.request().getParam("x"));
			double y = Double.parseDouble(ctx.request().getParam("y"));
			// 클라이언트 좌표값

			rs = MySQL.executeQuery(query.split(" ORDER BY")[0]);
			// contentTypeId에 해당하는 데이터 전체

			rs = distanceBasedInquiry(rs, page, x, y);
			// 거리기반 조회

			break;
		}
		
		return extractDatas(ctx, rs);
	}
	
	public static JSONArray inquire(RoutingContext ctx, String category) {
		// 카테고리 필터링
		int sortType = Integer.parseInt(ctx.request().getParam("sort_type"));
		int page = Integer.parseInt(ctx.request().getParam("page"));

		String query = null;
		if(category.length() == 3) {
			query = "SELECT * FROM attractions_basic WHERE cat1=? ORDER BY ? LIMIT " + (page - 1) * numOfRows + ", " + page * numOfRows;
		} else if(category.length() == 5) {
			query = "SELECT * FROM attractions_basic WHERE cat2=? ORDER BY ? LIMIT " + (page - 1) * numOfRows + ", " + page * numOfRows;
		} else {
			query ="SELECT * FROM attractions_basic WHERE cat3=? ORDER BY ? LIMIT " + (page - 1) * numOfRows + ", " + page * numOfRows;
		}
		ResultSet rs = null;

		switch (sortType) {
		case 1:
			// 조회순
			rs = MySQL.executeQuery(query.replaceFirst("\\?", "'" + category + "'").replaceFirst("\\?", "views_count DESC"));
			break;
		case 2:
			// 위시리스트 많은 순
			rs = MySQL.executeQuery(query.replaceFirst("\\?", "'" + category + "'").replaceFirst("\\?", "wish_count DESC"));
			break;
		case 3:
			// 거리순
			double x = Double.parseDouble(ctx.request().getParam("x"));
			double y = Double.parseDouble(ctx.request().getParam("y"));
			// 클라이언트 좌표값

			rs = MySQL.executeQuery(query.split(" ORDER BY")[0], category);
			// contentTypeId에 해당하는 데이터 전체

			rs = distanceBasedInquiry(rs, page, x, y);
			// 거리기반 조회

			break;
		}
		
		return extractDatas(ctx, rs);
	}
	
	public static JSONArray inquire(RoutingContext ctx, int contentTypeId) {
		// content type을 지정하여 정보 얻어오기
		int sortType = Integer.parseInt(ctx.request().getParam("sort_type"));
		int page = Integer.parseInt(ctx.request().getParam("page"));
		
		String query = "SELECT * FROM ? WHERE content_type_id=" + contentTypeId + " ORDER BY ? LIMIT " + (page - 1) * numOfRows + ", " + page * numOfRows;
		ResultSet rs = null;
		
		switch(sortType) {
		case 1:
			// 조회순
			rs = MySQL.executeQuery(query.replaceFirst("\\?", "attractions_basic").replaceFirst("\\?", "views_count DESC"));
			break;
		case 2:
			// 위시리스트 많은 순
			rs = MySQL.executeQuery(query.replaceFirst("\\?", "attractions_basic").replaceFirst("\\?", "wish_count DESC"));
			break;
		case 3:
			// 거리순
			double x = Double.parseDouble(ctx.request().getParam("x"));
			double y = Double.parseDouble(ctx.request().getParam("y"));
			// 클라이언트 좌표값
			
			rs = MySQL.executeQuery("SELECT * FROM attractions_basic WHERE content_type_id=?", contentTypeId);
			// contentTypeId에 해당하는 데이터 전체
			
			rs = distanceBasedInquiry(rs, page, x, y);
			// 거리기반 조회
			
			break;
		}

		return extractDatas(ctx, rs);
	}
	
	public static JSONArray inquire(RoutingContext ctx) {
		// content type 미지정 : 모든 타입에 대해 정보 얻어오기
		int sortType = Integer.parseInt(ctx.request().getParam("sort_type"));
		int page = Integer.parseInt(ctx.request().getParam("page"));
		
		String query = "SELECT * FROM ? ORDER BY ? LIMIT " + (page - 1) * numOfRows + ", " + page * numOfRows;
		ResultSet rs = null;
		
		switch(sortType) {
		case 1:
			// 조회순
			rs = MySQL.executeQuery(query.replaceFirst("\\?", "attractions_basic").replaceFirst("\\?", "views_count DESC"));
			break;
		case 2:
			// 위시리스트 많은 순
			rs = MySQL.executeQuery(query.replaceFirst("\\?", "attractions_basic").replaceFirst("\\?", "wish_count DESC"));
			break;
		case 3:
			// 거리순
			double x = Double.parseDouble(ctx.request().getParam("x"));
			double y = Double.parseDouble(ctx.request().getParam("y"));
			// 클라이언트 좌표값
			
			rs = MySQL.executeQuery("SELECT * FROM attractions_basic");
			// 데이터 전체
			
			rs = distanceBasedInquiry(rs, page, x, y);
			// 거리기반 조회
			
			break;
		}

		return extractDatas(ctx, rs);
	}
	
	private static ResultSet distanceBasedInquiry(ResultSet rs, int page, double clientX, double clientY) {
		/*
		 * 거리기반 조회 : 전체 데이터를 가지고 재정렬
		 * 조회할 수 있는 데이터가 없는 경우 null 리턴됨
		 */
		
		Map<Integer, Double> distances = new HashMap<>();
		/*
		 * 클라이언트와 여행지 사이의 거리를 가지고 있는 HashMap
		 * Key : Content ID
		 * Value : 클라이언트와 여행지 사이의 거리
		 */
		
		ValueComparator vc = new ValueComparator(distances);
		// Map의 Value 기반 오름차순 정렬을 위한 Comparator
		
		Map<Integer, Double> sortedMap = new TreeMap<>(vc);
		// ValueComparator를 생성자 파라미터로 가진 Value 기반 정렬된 TreeMap
		
		try {
			while(rs.next()) {
				// ResultSet의 한계점까지
				
				int contentId = rs.getInt("content_id");
				// 여행지의 id
				
				double attractionX = rs.getDouble("mapx");
				double attractionY = rs.getDouble("mapy");
				// 여행지의 좌표값
				
				double distance = Math.sqrt(Math.pow(clientX - attractionX, 2) + Math.pow(clientY - attractionY, 2));
				// 클라이언트와의 거리
				
				distances.put(contentId, distance);
				// Map에 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sortedMap.putAll(distances);
		// 정렬된 map
		
		Iterator<Integer> contentIdIterator = sortedMap.keySet().iterator();
		// 정렬된 Map의 반복자
		
		for(int i = 0; i < (page - 1) * numOfRows; i++) {
			/*
			 * 조회하는 페이지 이전 데이터를 지나치기 위한 반복문
			 * 조회하는 페이지가 4페이지라면, 1~3페이지 데이터를 next로 스킵
			 */
			if(contentIdIterator.hasNext()) {
				contentIdIterator.next();
			}
		}
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM attractions_basic WHERE content_id=");
		// 거리순으로 조회할 새로운 쿼리
		
		for(int i = 0; i < numOfRows; i++) {
			if(contentIdIterator.hasNext()) {
				int contentId = contentIdIterator.next();
				if(i < numOfRows - 1) {
					query.append(contentId).append(" OR content_id=");
				} else {
					query.append(contentId);
				}
			} else {
				query.append(0);
				/*
				 * 페이지 순회 중 데이터가 모두 소진되었거나 데이터가 없는 경우
				 * 쿼리 끝의 OR ~를 삭제할 수 있지만 if절 낭비를 줄이기 위해 0으로 채워줌
				 */
			}
		}
		
		return MySQL.executeQuery(query.toString());
	}
	
	private static JSONArray extractDatas(RoutingContext ctx, ResultSet rs) {
		/*
		 * ResultSet에 대한 데이터 추출기
		 * 뽑을 데이터가 하나도 없다면 객체 그대로(null) 리턴
		 */
		JSONArray response = new JSONArray();
		try {
			while(rs.next()) {
				JSONObject attractionInfo = new JSONObject();
				ResultSet wish = MySQL.executeQuery("SELECT * FROM wish_list WHERE client_id=? AND content_id=?", UserManager.getEncryptedIdFromSession(ctx), rs.getInt("content_id"));
				attractionInfo.put("wish", wish != null ? wish.next() : false);
				
				attractionInfo.put("wish_count", rs.getInt("wish_count"));
				attractionInfo.put("content_id", rs.getInt("content_id"));
				attractionInfo.put("title", rs.getString("title"));
				attractionInfo.put("address", rs.getString("address"));
				attractionInfo.put("category", rs.getString("cat3"));
				attractionInfo.put("image", rs.getString("image_big_url"));
				attractionInfo.put("mapx", rs.getDouble("mapx"));
				attractionInfo.put("mapy", rs.getDouble("mapy"));
				response.put(attractionInfo);
			}
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}
}
