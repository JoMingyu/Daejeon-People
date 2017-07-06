package com.planb.parser.support;

public enum ParserBase {
	// 베이스 파라미터(areaCode=3(대전광역시), 서비스키, MobileOS, MobileApp, type 명시)
	
	DEF_PARAM("?areaCode=3&ServiceKey=bb%2FPPi9Iy9rNdmIN7PIdb4doQ8PCwL725OFZndZ7DS%2FbP8%2Bzr9T3rpoD%2B083JYDwg5YJyi3HQ3UZ5%2Fp0e6ER8Q%3D%3D&MobileOS=AND&MobileApp=DaejeonPeople&_type=json"),
	DEF_PARAM2("?areaCode=3&ServiceKey=48I9bBbLhZbPJqPjm5Z1MQL4KzXCFjyItb1mueqElgM0F6F3aB7AcQwJLDEjjjFXCKsIlgSJRRY7FmrgtcPDPw%3D%3D&MobileOS=AND&MobileApp=DaejeonPeople&_type=json");
	
	private final String name;
	
	ParserBase(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
