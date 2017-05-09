package com.planb.api.parser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.planb.api.support.Params;
import com.planb.api.support.Request;
import com.planb.support.utilities.DataBase;

public class DetailCommonParser {
	/*
	 * 공통정보 조회
	 * 홈페이지와 개요 정보
	 */
	private static String URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon" + Params.defaultAppendParams;
	private static DataBase database = DataBase.getInstance();
	
	public static void parse() {
		database.executeUpdate("DELETE FROM attractions_detail_common");
		URL = URL + "&defaultYN=Y&overviewYN=Y";
		
		ResultSet rs = database.executeQuery("SELECT * FROM attractions_basic");
		Map<Integer, Integer> contentInfoMap = new HashMap<Integer, Integer>();
		
		try {
			while(rs.next()) {
				contentInfoMap.put(rs.getInt("content_id"), rs.getInt("content_type_id"));
			}
			// To escape SQLException : Operation not allowed after ResultSet closed
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		for(int contentId : contentInfoMap.keySet()) {
			int contentTypeId = contentInfoMap.get(contentId);
			JSONObject item = Request.getItem(URL + "&contentId=" + contentId);
			
			String homepage = item.has("homepage") ? item.getString("homepage").replaceAll("'", "''") : null;
			// 홈페이지 주소
			
			String overview = item.has("overview") ? item.getString("overview").replaceAll("'", "''") : null;
			// 개요
			
			String telName = item.has("telname") ? item.getString("telname") : null;
			// 전화번호명
			
			String tel = item.has("tel") ? item.getString("tel") : null;
			// 전화번호
			
			database.executeUpdate("INSERT INTO attractions_detail_common VALUES(", contentId, ", ", contentTypeId, ", '", homepage, "', '", overview, "', '", telName, "', '", tel, "')");
		}
		System.out.println("Detail Common Parse Success.");
	}
}
