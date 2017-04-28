package com.planb.support.attractions;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.planb.support.database.DataBase;

public class AttractionsListInquiry {
	private static DataBase database = DataBase.getInstance();
	public static JSONArray getData(int contentTypeId, int sortType, int page) {
		// content type을 지정하여 정보 얻어오기
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
			break;
		}
		
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
	
	public static JSONArray getTotalData(int sortType, int page) {
		// content type 미지정 : 모든 타입에 대해 정보 얻어오기
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
			break;
		}
		
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
