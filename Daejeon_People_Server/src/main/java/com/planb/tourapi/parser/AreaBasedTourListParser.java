package com.planb.tourapi.parser;

import com.planb.support.database.DataBase;
import com.planb.tourapi.support.Params;

public class AreaBasedTourListParser {
	/*
	 * 지역기반 관광정보 리스트 조회
	 * JsonArray를 순차 탐색하며 DB 저장
	 */
	private static String URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList".concat(Params.defaultAppendParams);
	private static DataBase database = DataBase.getInstance();
	
	public static void parse() {
		
	}
}
