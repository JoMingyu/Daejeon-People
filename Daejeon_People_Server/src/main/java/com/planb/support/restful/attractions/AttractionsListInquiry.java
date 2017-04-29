package com.planb.support.restful.attractions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.database.DataBase;

import io.vertx.ext.web.RoutingContext;

public class AttractionsListInquiry {
	private static DataBase database = DataBase.getInstance();
	public static JSONArray getDatas(RoutingContext ctx, int contentTypeId) {
		// content type을 지정하여 정보 얻어오기
		int sortType = Integer.parseInt(ctx.request().getParam("sort_type"));
		int page = Integer.parseInt(ctx.request().getParam("page"));
		int numOfRows = AttractionsConfig.NUM_OF_ROWS;
		String query = "SELECT * FROM %s ORDER BY %s LIMIT " + (page - 1) * numOfRows + ", " + numOfRows + " WHERE content_type_id=" + contentTypeId;
		ResultSet rs = null;
		
		switch(sortType) {
		case 1:
			// 조회순
			rs = database.executeQuery(String.format(query, "attractions_basic", "views_count DESC"));
			break;
		case 2:
			// 좋아요순
			rs = database.executeQuery(String.format(query, "attractions_basic", "like_count DESC"));
			break;
		case 3:
			// 거리순
			double x = Double.parseDouble(ctx.request().getParam("x"));
			double y = Double.parseDouble(ctx.request().getParam("y"));
			// 클라이언트 좌표값
			
			rs = database.executeQuery("SELECT * FROM attractions_basic WHERE content_type_id=", contentTypeId);
			// contentTypeId에 해당하는 데이터 전체
			
			Map<Integer, Double> attractionDistances = new HashMap<Integer, Double>();
			// 클라이언트와 여행지 사이의 거리를 가지고 있는 HashMap
			
			ValueComparator vc = new ValueComparator(attractionDistances);
			Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(vc);
			// 정렬을 위한 Comparator와 Map
			
			try {
				while(rs.next()) {
					// ResultSet의 한계점까지
					
					int contentId = rs.getInt("content_id");
					double attractionX = rs.getDouble("mapx");
					double attractionY = rs.getDouble("mapy");
					// 여행지 좌표값
					
					double distance = Math.sqrt(Math.pow(x - attractionX, 2) + Math.pow(y - attractionY, 2));
					// 거리
					
					attractionDistances.put(contentId, distance);
					// Map에 추가
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			sortedMap.putAll(attractionDistances);
			Set<Integer> contentIdSet = sortedMap.keySet();
			Iterator<Integer> contentIdIterator = contentIdSet.iterator();
			
			for(int i = 0; i < (page - 1) * numOfRows; i++) {
				// 조회하는 페이지 이전 데이터를 지나치기 위한 반복문
				if(contentIdIterator.hasNext()) {
					contentIdIterator.next();
				} else {
					// 반복자가 참조할 다음 값이 없을 경우(해당 페이지에 데이터가 없는 경우)
					break;
				}
			}
			
			StringBuilder queryClone = new StringBuilder();
			queryClone.append("SELECT * FROM attractions_basic WHERE content_id=");
			for(int i = 0; i < numOfRows; i++) {
				if(contentIdIterator.hasNext()) {
					int contentId = contentIdIterator.next();
					if(i < numOfRows - 1) {
						queryClone.append(contentId + " OR content_id=");
					} else {
						queryClone.append(contentId);
					}
				}
			}
			rs = database.executeQuery(queryClone.toString());
			
			break;
		}
		
		JSONArray result = extractDatas(rs);
		
		return result;
	}
	
	public static JSONArray getTotalDatas(RoutingContext ctx) {
		// content type 미지정 : 모든 타입에 대해 정보 얻어오기
		int sortType = Integer.parseInt(ctx.request().getParam("sort_type"));
		int page = Integer.parseInt(ctx.request().getParam("page"));
		int numOfRows = AttractionsConfig.NUM_OF_ROWS;
		String query = "SELECT * FROM %s ORDER BY %s LIMIT " + (page - 1) * numOfRows + ", " + numOfRows;
		ResultSet rs = null;
		
		switch(sortType) {
		case 1:
			// 조회순
			rs = database.executeQuery(String.format(query, "attractions_basic", "views_count DESC"));
			break;
		case 2:
			// 좋아요순
			rs = database.executeQuery(String.format(query, "attractions_basic", "like_count DESC"));
			break;
		case 3:
			// 거리순
			double x = Double.parseDouble(ctx.request().getParam("x"));
			double y = Double.parseDouble(ctx.request().getParam("y"));
			// 클라이언트 좌표값
			
			rs = database.executeQuery("SELECT * FROM attractions_basic");
			// 데이터 전체
			
			Map<Integer, Double> attractionDistances = new HashMap<Integer, Double>();
			// 클라이언트와 여행지 사이의 거리를 가지고 있는 HashMap
			
			ValueComparator vc = new ValueComparator(attractionDistances);
			Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(vc);
			// 정렬을 위한 Comparator와 Map
			
			try {
				while(rs.next()) {
					// ResultSet의 한계점까지
					
					int contentId = rs.getInt("content_id");
					double attractionX = rs.getDouble("mapx");
					double attractionY = rs.getDouble("mapy");
					// 여행지 좌표값
					
					double distance = Math.sqrt(Math.pow(x - attractionX, 2) + Math.pow(y - attractionY, 2));
					// 거리
					
					attractionDistances.put(contentId, distance);
					// Map에 추가
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			sortedMap.putAll(attractionDistances);
			Set<Integer> contentIdSet = sortedMap.keySet();
			Iterator<Integer> contentIdIterator = contentIdSet.iterator();
			
			for(int i = 0; i < (page - 1) * numOfRows; i++) {
				// 조회하는 페이지 이전 데이터를 지나치기 위한 반복문
				if(contentIdIterator.hasNext()) {
					contentIdIterator.next();
				} else {
					// 반복자가 참조할 다음 값이 없을 경우(해당 페이지에 데이터가 없는 경우)
					break;
				}
			}
			
			StringBuilder queryClone = new StringBuilder();
			queryClone.append("SELECT * FROM attractions_basic WHERE content_id=");
			for(int i = 0; i < numOfRows; i++) {
				if(contentIdIterator.hasNext()) {
					int contentId = contentIdIterator.next();
					if(i < numOfRows - 1) {
						queryClone.append(contentId + " OR content_id=");
					} else {
						queryClone.append(contentId);
					}
				}
			}
			rs = database.executeQuery(queryClone.toString());
			
			break;
		}
		
		JSONArray result = extractDatas(rs);
		
		return result;
	}
	
	private static JSONArray extractDatas(ResultSet rs) {
		JSONArray result = new JSONArray();
		try {
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("address", rs.getString("address"));
				obj.put("category", rs.getString("cat3"));
				obj.put("content_id", rs.getInt("content_id"));
				obj.put("image", rs.getString("image_mini_url"));
				obj.put("mapx", rs.getDouble("mapx"));
				obj.put("mapy", rs.getDouble("mapy"));
				obj.put("title", rs.getString("title"));
				result.put(obj);
			}
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
