package com.planb.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.planb.parser.support.BaseURLs;
import com.planb.parser.support.HttpClientForParser;
import com.planb.support.utilities.Log;
import com.planb.support.utilities.MySQL;

public class DetailCommonParser implements Parser {
	// 공통 세부 정보 파서
	
	private static final String URL = BaseURLs.DETAIL_COMMON.getName();
	
	private void clearTables() {
		MySQL.executeUpdate("DELETE FROM attractions_detail_common");
	}
	
	@Override
	public void parse() {
		clearTables();
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM attractions_basic");
		
		try {
			while(rs != null ? rs.next() : false) {
				int contentId = rs.getInt("content_id");
				int contentTypeId = rs.getInt("content_type_id");
				
				JSONObject item = HttpClientForParser.getItem(URL + "&contentId=" + contentId);
				if(item == null) {
					continue;
				}
				
				String homepage = item.has("homepage") ? item.getString("homepage").replaceAll("'", "''") : null;
				// 홈페이지 주소
				
				String overview = item.has("overview") ? item.getString("overview").replaceAll("'", "''") : null;
				// 개요
				
				String telName = item.has("telname") ? item.getString("telname") : null;
				// 전화번호명
				
				String tel = item.has("tel") ? item.getString("tel") : null;
				// 전화번호
				
				MySQL.executeUpdate("INSERT INTO attractions_detail_common VALUES(?, ?, ?, ?, ?, ?)", contentId, contentTypeId, homepage, overview, telName, tel);
			}
			
			Log.info("Detail Common Parse Success.");
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
