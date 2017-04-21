package com.planb.support.mail;

public enum MailSubjects {
	VERIFY_SUBJECT("[대전사람] 회원가입 인증 코드입니다."),
	WELCOME_SUBJECT("[대전사람] 회원가입을 환영합니다."),
	FIND_ID_SUBJECT("[대전사람] 아이디 찾기 결과입니다."),
	FIND_PASSWORD_SUBJECT("[대전사람] 임시 비밀번호입니다.");
	
	private final String name;
	
	private MailSubjects(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
