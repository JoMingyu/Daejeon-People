package com.planb.api.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.planb.api.support.HttpRequestForParser;
import com.planb.api.support.Params;
import com.planb.support.utilities.DataBase;
import com.planb.support.utilities.Log;

public class DetailCommonParser {
	/*
	 * 공통정보 조회
	 * 홈페이지와 개요 정보
	 */
	private static String URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon" + Params.defaultAppendParams + "&defaultYN=Y&overviewYN=Y";
	
	public static void parse() {
		DataBase.executeUpdate("DELETE FROM attractions_detail_common");
		
		ResultSet rs = DataBase.executeQuery("SELECT * FROM attractions_basic");
		
		try {
			while(rs.next()) {
				int contentId = rs.getInt("content_id");
				int contentTypeId = rs.getInt("content_type_id");
				
				JSONObject item = HttpRequestForParser.getItem(URL + "&contentId=" + contentId);
				
				String homepage = item.has("homepage") ? item.getString("homepage").replaceAll("'", "''") : null;
				// 홈페이지 주소
				
				String overview = item.has("overview") ? item.getString("overview").replaceAll("'", "''") : null;
				// 개요
				
				String telName = item.has("telname") ? item.getString("telname") : null;
				// 전화번호명
				
				String tel = item.has("tel") ? item.getString("tel") : null;
				// 전화번호
				
				DataBase.executeUpdate("INSERT INTO attractions_detail_common VALUES(", contentId, ", ", contentTypeId, ", '", homepage, "', '", overview, "', '", telName, "', '", tel, "')");
			}
			
			Log.I("Detail Common Parse Success.");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
