package com.planb.api.parser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.api.support.Params;
import com.planb.api.support.Request;
import com.planb.support.database.DataBase;

public class AdditionalImageParser {
	private static String defaultURL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage" + Params.defaultAppendParams + "&imageYN=Y";
	private static DataBase database = DataBase.getInstance();
	
	public static void parse() {
		database.executeUpdate("DELETE FROM attractions_images");
		
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
		
		while(contentIdIterator.hasNext()) {
			int contentId = contentIdIterator.next();
			int contentTypeId = contentInfoMap.get(contentId);
			String requestURL = defaultURL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId;
			int totalCount = Request.getTotalCount(requestURL);
			
			if(totalCount == 0) {
				continue;
			}
			
			if(totalCount == 1) {
				JSONObject item = Request.getItem(requestURL);
				database.executeUpdate("INSERT INTO attractions_images VALUES(", contentId, ", '", item.getString("originimgurl"), "')");
			} else {
				// totalCount > 1
				
				JSONArray items = Request.getItems(requestURL + "&numOfRows=" + totalCount);
				// 응답받을 이미지 갯수를 totalCount에 맞춰서 요청
				
				for(int i = 0; i < items.length(); i++) {
					JSONObject item = items.getJSONObject(i);
					database.executeUpdate("INSERT INTO attractions_images VALUES(", contentId, ", '", item.getString("originimgurl"), "')");
				}
			}
			
		}
		System.out.println("Additional Image Parse Success.");
	}
}
