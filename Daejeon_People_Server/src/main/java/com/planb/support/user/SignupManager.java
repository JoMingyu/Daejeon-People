package com.planb.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.planb.support.crypto.AES256;
import com.planb.support.crypto.SHA256;
import com.planb.support.utilities.DataBase;
import com.planb.support.utilities.Mail;
import com.planb.support.utilities.MailSubjects;
import com.sun.javafx.binding.StringFormatter;

public class SignupManager {
	private static DataBase database = DataBase.getInstance();
	private static AES256 aes = new AES256("d.df!*&ek@s.Cde/q");
	/*
	 * ID, Email, Tel, Name : AES256
	 * PW, sessionId : SHA256
	 */
	private static ResultSet rs;
	
	public static boolean checkPhoneNumberExists(String phoneNumber) {
		/*
		 * 핸드폰 번호 존재 여부 체크
		 * 존재 시 true, 실패 시 false
		 */
		String encryptedPhoneNumber = aes.encrypt(phoneNumber);
		
		rs = database.executeQuery("SELECT * FROM account WHERE phone_number='", encryptedPhoneNumber, "'");
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
	
	public static void demandPhone(String phoneNumber) {
		/*
		 * 문자 메시지 인증코드 전송
		 */
		String encryptedPhoneNumber = aes.encrypt(phoneNumber);
		// 핸드폰 번호 암호화
		
		Random random = new Random();
		String code = StringFormatter.format("%06d", random.nextInt(1000000)).getValue();
		// 인증코드 생성
		
		database.executeUpdate("DELETE FROM phone_verify_codes WHERE phone_number='", encryptedPhoneNumber, "'");
		database.executeUpdate("INSERT INTO phone_verify_codes VALUES('", encryptedPhoneNumber, "', '", code, "')");
		// 인증코드 insert or refresh
		
		// 인증코드 전송(보류)
	}
	
	public static boolean verifyPhone(String phoneNumber, String code) {
		/*
		 * 인증코드 인증
		 * 성공 시 true, 실패 시 false
		 */
		String encryptedPhoneNumber = aes.encrypt(phoneNumber);

		rs = database.executeQuery("SELECT * FROM phone_verify_codes WHERE phone_number='", encryptedPhoneNumber, "' AND code='", code, "'");
		try {
			if (rs.next()) {
				database.executeUpdate("DELETE FROM phone_verify_codes WHERE phone_number='", encryptedPhoneNumber, "' AND code='", code, "'");
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean checkEmailExists(String email) {
		/*
		 *  이메일 존재 여부 체크
		 *  존재 시 true, 실패 시 false
		 */
		String encryptedEmail = aes.encrypt(email);

		rs = database.executeQuery("SELECT * FROM account WHERE email='", encryptedEmail, "'");
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

	public static void demandEmail(String email) {
		/*
		 * 이메일 전송
		 */
		String encryptedEmail = aes.encrypt(email);
		// 이메일 암호화

		Random random = new Random();
		String code = StringFormatter.format("%06d", random.nextInt(1000000)).getValue();
		// 이메일 인증코드 생성
		
		database.executeUpdate("DELETE FROM email_verify_codes WHERE email='", encryptedEmail, "'");
		database.executeUpdate("INSERT INTO email_verify_codes VALUES('", encryptedEmail, "', '", code, "')");
		// 인증코드 insert or refresh
		
		Mail.sendMail(email, MailSubjects.VERIFY_SUBJECT.getName(), "코드 : " + code);
		// 인증코드 전송
	}

	public static boolean verifyEmail(String email, String code) {
		/*
		 * 인증코드 인증
		 * 성공 시 true, 실패 시 false
		 */
		String encryptedEmail = aes.encrypt(email);

		rs = database.executeQuery("SELECT * FROM email_verify_codes WHERE email='", encryptedEmail, "' AND code='", code, "'");
		try {
			if (rs.next()) {
				database.executeUpdate("DELETE FROM email_verify_codes WHERE email='", encryptedEmail, "' AND code='", code, "'");
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean checkIdExists(String id) {
		/*
		 *  아이디 존재 여부 체크
		 *  존재 시 true, 실패 시 false
		 */
		String encryptedId = aes.encrypt(id);

		rs = database.executeQuery("SELECT * FROM account WHERE id='", encryptedId, "'");
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

	public static void signup(String id, String password, String email, String phoneNumber, String name, String registrationId) {
		/*
		 * 회원가입
		 * id와 이메일 중복 체크는 다른 URI에서 수행
		 */
		String encryptedId = aes.encrypt(id);
		String encryptedPassword = SHA256.encrypt(password);
		String encryptedEmail = aes.encrypt(email);
		String encryptedPhoneNumber = phoneNumber == null ? null : aes.encrypt(phoneNumber);
		// null이면 null, null이 아니면 암호화
		String encryptedName = aes.encrypt(name);
		String encryptedRegistrationId = aes.encrypt(registrationId);
		
		if(encryptedPhoneNumber == null) {
			database.executeUpdate("INSERT INTO account(id, password, email, phone_number, name, register_date, registration_id) VALUES('", encryptedId, "', '", encryptedPassword, "', '", encryptedEmail, "', null, '", encryptedName, "', now(), '", encryptedRegistrationId, "')");
		} else {
			database.executeUpdate("INSERT INTO account(id, password, email, phone_number, name, register_date, registration_id) VALUES('", encryptedId, "', '", encryptedPassword, "', '", encryptedEmail, "', '", encryptedPhoneNumber, "', '", encryptedName, "', now(), '", encryptedRegistrationId, "')");
		}
		Mail.sendMail(email, MailSubjects.WELCOME_SUBJECT.getName(), "환영환영");
	}
}
