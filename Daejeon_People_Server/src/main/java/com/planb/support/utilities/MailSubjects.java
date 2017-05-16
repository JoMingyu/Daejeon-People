package com.planb.support.utilities;

public enum MailSubjects {
	VERIFY_SUBJECT("[대전사람] 회원가입 인증 코드입니다."),
	FIND_ID_DEMAND_SUBJECT("[대전사람] 아이디 찾기 인증 코드입니다."),
	FIND_PW_DEMAND_SUBJECT("[대전사람] 아이디 찾기 인증 코드입니다."),
	WELCOME_SUBJECT("[대전사람] 회원가입을 환영합니다."),
	FIND_ID_RESULT_SUBJECT("[대전사람] 아이디 찾기 결과입니다."),
	FIND_PW_RESULT_SUBJECT("[대전사람] 아이디 찾기 결과입니다.");
	
	private final String name;
	
	private MailSubjects(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
