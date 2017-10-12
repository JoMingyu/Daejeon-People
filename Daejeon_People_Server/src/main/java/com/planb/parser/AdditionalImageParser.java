package com.planb.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.parser.support.BaseURLs;
import com.planb.parser.support.HttpClientForParser;
import com.planb.support.utilities.Log;
import com.planb.support.utilities.MySQL;

public class AdditionalImageParser implements Parser {
	// 추가 이미지 파서
	
	private static final String defaultURL = BaseURLs.ADDITIONAL_IMAGE.getName();
	
	private void clearTables() {
		MySQL.executeUpdate("DELETE FROM attractions_images");
	}
	
	@Override
	public void parse() {
		clearTables();
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM attractions_basic");
		
		try {
			while(rs != null && rs.next()) {
				int contentId = rs.getInt("content_id");
				int contentTypeId = rs.getInt("content_type_id");
				
				String requestURL = defaultURL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId;
				
				int totalCount = HttpClientForParser.getTotalCount(requestURL);
				if(totalCount == 0) {
					continue;
				}
				
				if(totalCount == 1) {
					JSONObject item = HttpClientForParser.getItem(requestURL);
					MySQL.executeUpdate("INSERT INTO attractions_images VALUES(?, ?)", contentId, item != null ? item.getString("originimgurl") : null);
				} else {
					// totalCount > 1
					
					JSONArray items = HttpClientForParser.getItems(requestURL + "&numOfRows=" + totalCount);
					// 응답받을 이미지 갯수를 totalCount에 맞춰서 요청
					
					for(int i = 0; i < items.length(); i++) {
						JSONObject item = items.getJSONObject(i);
						MySQL.executeUpdate("INSERT INTO attractions_images VALUES(?, ?)", contentId, item.getString("originimgurl"));
					}
				}
			}
			
			Log.info("Additional Image Parse Success.");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
