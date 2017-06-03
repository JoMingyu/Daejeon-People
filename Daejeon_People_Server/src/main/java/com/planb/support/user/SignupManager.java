package com.planb.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.planb.support.crypto.AES256;
import com.planb.support.crypto.SHA256;
import com.planb.support.utilities.MySQL;
import com.planb.support.utilities.Mail;
import com.planb.support.utilities.MailSubjects;

public class SignupManager {
	/*
	 * ID : AES256
	 * Registration ID : AES256
	 * Email, Name, Tel : AES256
	 * PW, Session ID : SHA256
	 */
	private static ResultSet rs;
	
	private static boolean checkPhoneNumberExists(String phoneNumber) {
		rs = MySQL.executeQuery("SELECT * FROM account WHERE phone_number=?", AES256.encrypt(phoneNumber));
		try {
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static int demandPhone(String phoneNumber) {
		/*
		 * 문자 메시지 인증코드 전송
		 */
		if(checkPhoneNumberExists(phoneNumber)) {
			return 204;
		}
		
		String encryptedPhoneNumber = AES256.encrypt(phoneNumber);
		// 핸드폰 번호 암호화
		
		Random random = new Random();
		String code = String.format("%06d", random.nextInt(1000000));
		// 인증코드 생성
		
		MySQL.executeUpdate("DELETE FROM phone_verify_codes WHERE phone_number=?", encryptedPhoneNumber);
		MySQL.executeUpdate("INSERT INTO phone_verify_codes VALUES(?, ?)", encryptedPhoneNumber, code);
		// 인증코드 insert or refresh
		
		// 인증코드 전송(보류)
		
		return 201;
	}
	
	public static int verifyPhone(String phoneNumber, String code) {
		String encryptedPhoneNumber = AES256.encrypt(phoneNumber);

		rs = MySQL.executeQuery("SELECT * FROM phone_verify_codes WHERE phone_number=? AND code=?", encryptedPhoneNumber, code);
		try {
			if (rs.next()) {
				MySQL.executeUpdate("DELETE FROM phone_verify_codes WHERE phone_number=? AND code=?", encryptedPhoneNumber, code);
				return 201;
			} else {
				return 204;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 204;
		}
	}
	
	private static boolean checkEmailExists(String email) {
		rs = MySQL.executeQuery("SELECT * FROM account WHERE email=?", AES256.encrypt(email));
		try {
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int demandEmail(String email) {
		/*
		 * 이메일 전송
		 */
		if(checkEmailExists(email)) {
			return 204;
		}
		
		String encryptedEmail = AES256.encrypt(email);
		// 이메일 암호화

		Random random = new Random();
		String code = String.format("%06d", random.nextInt(1000000));
		// 이메일 인증코드 생성
		
		MySQL.executeUpdate("DELETE FROM email_verify_codes WHERE email=?", encryptedEmail);
		MySQL.executeUpdate("INSERT INTO email_verify_codes VALUES(?, ?)", encryptedEmail, code);
		// Refresh
		
		Mail.sendMail(email, MailSubjects.VERIFY_SUBJECT.getName(), "코드 : " + code);
		// 인증코드 전송
		
		return 201;
	}

	public static int verifyEmail(String email, String code) {
		String encryptedEmail = AES256.encrypt(email);

		rs = MySQL.executeQuery("SELECT * FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
		try {
			if (rs.next()) {
				MySQL.executeUpdate("DELETE FROM email_verify_codes WHERE email=? AND code=?", encryptedEmail, code);
				return 201;
			} else {
				return 204;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 204;
		}
	}

	public static int checkIdExists(String id) {
		rs = MySQL.executeQuery("SELECT * FROM account WHERE id=?", AES256.encrypt(id));
		try {
			if (rs.next()) {
				return 204;
			} else {
				return 201;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return 204;
		}
	}

	public static int signup(String id, String password, String email, String phoneNumber, String name, String registrationId) {
		/*
		 * 회원가입
		 * 중복 체크와 인증은 다른 URI에서 수행
		 */
		String encryptedId = AES256.encrypt(id);
		String encryptedPassword = SHA256.encrypt(password);
		String encryptedEmail = AES256.encrypt(email);
		String encryptedPhoneNumber = phoneNumber == null ? null : AES256.encrypt(phoneNumber);
		// null이면 null, null이 아니면 암호화
		String encryptedName = AES256.encrypt(name);
		String encryptedRegistrationId = AES256.encrypt(registrationId);
		
		if(encryptedPhoneNumber == null) {
			MySQL.executeUpdate("INSERT INTO account(id, password, email, phone_number, name, register_date, registration_id) VALUES(?, ?, ?, null, ?, NOW(), ?)", encryptedId, encryptedPassword, encryptedEmail, encryptedName, encryptedRegistrationId);
		} else {
			MySQL.executeUpdate("INSERT INTO account(id, password, email, phone_number, name, register_date, registration_id) VALUES(?, ?, ?, ?, ?, now(), ?)", encryptedId, encryptedPassword, encryptedEmail, encryptedPhoneNumber, encryptedName, encryptedRegistrationId);
		}
		
		Mail.sendMail(email, MailSubjects.WELCOME_SUBJECT.getName(), "환영환영");
		
		return 201;
	}
}
