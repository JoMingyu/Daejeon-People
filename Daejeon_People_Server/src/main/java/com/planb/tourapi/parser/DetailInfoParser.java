package com.planb.tourapi.parser;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.planb.support.database.DataBase;
import com.planb.tourapi.support.Params;
import com.planb.tourapi.support.Request;

public class DetailInfoParser {
	private static String URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro" + Params.defaultAppendParams;
	private static DataBase database = DataBase.getInstance();
	
	public static void parse() {
		/*
		 *  파싱 한 번에 336 트래픽 발생, 주의 필요
		 *  연속 200트래픽 이후 잠시동안 응답을 하지 않음
		 */
		ResultSet rs = database.executeQuery("SELECT * FROM attractions_basic");
		int count = 0;
		try {
			while(rs.next()) {
				int contentId = rs.getInt("content_id");
				int contentTypeId = rs.getInt("content_type_id");
				
				JSONObject item = Request.getItem(URL + "&contentId=" + contentId + "&contentTypeId=" + contentTypeId);
			}
			System.out.println("Detail Info Parse Success.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
