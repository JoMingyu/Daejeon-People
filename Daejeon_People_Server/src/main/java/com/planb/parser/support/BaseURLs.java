package com.planb.parser.support;

public enum BaseURLs {
	// 요청을 위한 base URL
	
	TOUR_LIST("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList" + ParserBase.DEF_PARAM.getName()),
	DETAIL_COMMON("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon" + ParserBase.DEF_PARAM.getName() + "&defaultYN=Y&overviewYN=Y"),
	DETAIL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro" + ParserBase.DEF_PARAM.getName()),
	ADDITIONAL_IMAGE("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage" + ParserBase.DEF_PARAM.getName() + "&imageYN=Y");
	
	private final String name;
	
	BaseURLs(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
