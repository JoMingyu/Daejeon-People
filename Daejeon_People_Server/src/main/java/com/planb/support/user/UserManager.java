package com.planb.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import com.planb.support.crypto.AES256;
import com.planb.support.crypto.SHA256;
import com.planb.support.database.DataBase;
import com.planb.support.mail.Mail;
import com.planb.support.mail.MailSubjects;
import com.sun.javafx.binding.StringFormatter;

import io.vertx.ext.web.RoutingContext;

public class UserManager {
	private DataBase database = DataBase.getInstance();
	private AES256 aes = new AES256("d.df!*&ek@s.Cde/q");
	/*
	 * ID, Email : AES256
	 * PW, sessionId : SHA256
	 */
	private ResultSet rs;

	public boolean checkEmailExists(String email) {
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

	public void demandEmail(String email) {
		String encryptedEmail = aes.encrypt(email);

		Random random = new Random();
		String code = StringFormatter.format("%06d", random.nextInt(1000000)).getValue();
		// 이메일 인증코드 생성
		
		database.executeUpdate("DELETE FROM verify_codes WHERE email='", encryptedEmail, "'");
		database.executeUpdate("INSERT INTO verify_codes VALUES('", encryptedEmail, "', '", code, "')");
		// 인증코드 insert or refresh
		
		Mail.sendMail(email, MailSubjects.VERIFY_SUBJECT.getName(), "코드 : ".concat(code));
		// 인증코드 전송
	}

	public boolean verifyEmail(String email, String code) {
		String encryptedEmail = aes.encrypt(email);

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

	public boolean checkIdExists(String id) {
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

	public void signup(String id, String email, String password) {
		String encryptedId = aes.encrypt(id);
		String encryptedEmail = aes.encrypt(email);
		String encryptedPassword = SHA256.encrypt(password);

		database.executeUpdate("INSERT INTO account(id, email, password) VALUES('", encryptedId, "', '", encryptedEmail, "', '", encryptedPassword, "')");
		Mail.sendMail(email, MailSubjects.WELCOME_SUBJECT.getName(), "환영환영");
	}

	public boolean signin(String id, String password) {
		String encryptedId = aes.encrypt(id);
		String encryptedPassword = SHA256.encrypt(password);

		rs = database.executeQuery("SELECT * FROM account WHERE id='", encryptedId, "' AND password='",
				encryptedPassword, "'");
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

	public String getIdFromSession(RoutingContext ctx) {
		return SessionUtil.getRegistedSessionKey(ctx, "UserSession");
	}
	
	public String getEncryptedSessionId(String id) {
		String encryptedId = aes.encrypt(id);
		String encryptedSessionId = null;
		
		rs = database.executeQuery("SELECT * FROM account WHERE id='", encryptedId, "'");
		try {
			rs.next();
			if(rs.getString("session_id") != null) {
				encryptedSessionId = rs.getString("session_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return encryptedSessionId;
	}

	public String createEncryptedSessionId() {
		String encryptedUUID;
		
		while(true) {
			encryptedUUID = SHA256.encrypt(UUID.randomUUID().toString());
			rs = database.executeQuery("SELECT * FROM account WHERE session_id='", encryptedUUID, "'");
			try {
				if(!rs.next()) {
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return encryptedUUID;
	}

	public void registerSessionId(RoutingContext ctx, boolean keepLogin, String id) {
		String encryptedSessionId = getEncryptedSessionId(id);
		String encryptedId = aes.encrypt(id);
		
		if(keepLogin) {
			if(encryptedSessionId == null) {
				encryptedSessionId = createEncryptedSessionId();
			}
			SessionUtil.createCookie(ctx, "UserSession", encryptedSessionId);
		} else {
			if(encryptedSessionId == null) {
				encryptedSessionId = createEncryptedSessionId();
			}
			SessionUtil.createSession(ctx, "UserSession", encryptedSessionId);
		}
		database.executeUpdate("UPDATE account SET session_id='", encryptedSessionId, "' WHERE id='", encryptedId, "'");
	}
	
	public boolean isLogined(RoutingContext ctx) {
		return ((getIdFromSession(ctx) == null) ? false : true);
	}
}
