package com.planb.api.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.planb.api.support.HttpRequestForParser;
import com.planb.api.support.Params;
import com.planb.support.utilities.DataBase;
import com.planb.support.utilities.Log;

public class AdditionalImageParser {
	private static String defaultURL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage" + Params.defaultAppendParams + "&imageYN=Y";
	
	public static void parse() {
		DataBase.executeUpdate("DELETE FROM attractions_images");
		
		ResultSet rs = DataBase.executeQuery("SELECT * FROM attractions_basic");
		
		try {
			while(rs.next()) {
				int contentId = rs.getInt("content_id");
				int contentTypeId = rs.getInt("content_type_id");
				
				String requestURL = defaultURL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId;
				
				int totalCount = HttpRequestForParser.getTotalCount(requestURL);
				if(totalCount == 0) {
					continue;
				}
				
				if(totalCount == 1) {
					JSONObject item = HttpRequestForParser.getItem(requestURL);
					DataBase.executeUpdate("INSERT INTO attractions_images VALUES(?, ?)", contentId, item.getString("originimgurl"));
				} else {
					// totalCount > 1
					
					JSONArray items = HttpRequestForParser.getItems(requestURL + "&numOfRows=" + totalCount);
					// 응답받을 이미지 갯수를 totalCount에 맞춰서 요청
					
					for(int i = 0; i < items.length(); i++) {
						JSONObject item = items.getJSONObject(i);
						DataBase.executeUpdate("INSERT INTO attractions_images VALUES(?, ?)", contentId, item.getString("originimgurl"));
					}
				}
			}
			
			Log.I("Additional Image Parse Success.");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
