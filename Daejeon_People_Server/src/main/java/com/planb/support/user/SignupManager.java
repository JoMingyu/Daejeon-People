package com.planb.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.planb.support.crypto.AES256;
import com.planb.support.crypto.SHA256;
import com.planb.support.database.DataBase;
import com.planb.support.mail.Mail;
import com.planb.support.mail.MailSubjects;
import com.sun.javafx.binding.StringFormatter;

public class SignupManager {
	private static DataBase database = DataBase.getInstance();
	private static AES256 aes = new AES256("d.df!*&ek@s.Cde/q");
	/*
	 * ID, Email, Tel, Name : AES256
	 * PW, sessionId : SHA256
	 */
	private static ResultSet rs;
	
	public static boolean checkEmailExists(String email) {
		/*
		 *  이메일 존재 여부 체크
		 *  존재 시 true, 실패 시 false
		 */
		String encryptedEmail = SHA256.encrypt(email);

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
		String encryptedEmail = SHA256.encrypt(email);
		// 이메일 암호화

		Random random = new Random();
		String code = StringFormatter.format("%06d", random.nextInt(1000000)).getValue();
		// 이메일 인증코드 생성
		
		database.executeUpdate("DELETE FROM verify_codes WHERE email='", encryptedEmail, "'");
		database.executeUpdate("INSERT INTO verify_codes VALUES('", encryptedEmail, "', '", code, "')");
		// 인증코드 insert or refresh
		
		Mail.sendMail(email, MailSubjects.VERIFY_SUBJECT.getName(), "코드 : " + code);
		// 인증코드 전송
	}

	public static boolean verifyEmail(String email, String code) {
		/*
		 * 인증코드 인증
		 * 성공 시 true, 실패 시 false
		 */
		String encryptedEmail = SHA256.encrypt(email);

		rs = database.executeQuery("SELECT * FROM verify_codes WHERE email='", encryptedEmail, "' AND code='", code, "'");
		try {
			if (rs.next()) {
				database.executeUpdate("DELETE FROM verify_codes WHERE email='", encryptedEmail, "' AND code='", code, "'");
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

	public static void signup(String id, String password, String email, String tel, String name) {
		/*
		 * 회원가입
		 * id와 이메일 중복 체크는 다른 URI에서 수행
		 */
		String encryptedId = aes.encrypt(id);
		String encryptedPassword = SHA256.encrypt(password);
		String encryptedEmail = aes.encrypt(email);
		String encryptedTel = tel == null ? null : aes.encrypt(tel);
		// null이면 null, null이 아니면 암호화
		String encryptedName = aes.encrypt(name);
		
		if(encryptedTel == null) {
			database.executeUpdate("INSERT INTO account(id, password, email, tel, name, register_date) VALUES('", encryptedId, "', '", encryptedPassword, "', '", encryptedEmail, "', null, '", encryptedName, "', now()", ")");
		} else {
			database.executeUpdate("INSERT INTO account(id, password, email, tel, name, register_date) VALUES('", encryptedId, "', '", encryptedPassword, "', '", encryptedEmail, "', '", encryptedTel, "', '", encryptedName, "', now()", ")");
		}
		Mail.sendMail(email, MailSubjects.WELCOME_SUBJECT.getName(), "환영환영");
	}
}
