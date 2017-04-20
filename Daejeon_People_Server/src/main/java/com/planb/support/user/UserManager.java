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

public class UserManager implements AccountManageable {
	private DataBase database = DataBase.getInstance();
	private AES256 aes = new AES256("d.df!*&ek@s.Cde/q");
	/*
	 * ID, Email : AES256
	 * PW, sessionId : SHA256
	 */
	private ResultSet rs;

	@Override
	public OperationResult checkEmailExists(String email) {
		OperationResult result = new OperationResult();
		String encryptedEmail = aes.encrypt(email);

		rs = database.executeQuery("SELECT * FROM account WHERE email='", encryptedEmail, "'");
		try {
			if (rs.next()) {
				// 이메일 존재
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public OperationResult demandEmail(String email) {
		OperationResult result = new OperationResult();
		String encryptedEmail = aes.encrypt(email);

		Random random = new Random();
		String code = StringFormatter.format("%06d", random.nextInt(1000000)).getValue();
		// 이메일 인증코드 생성
		
		database.executeUpdate("DELETE FROM verify_codes WHERE email='", encryptedEmail, "'");
		database.executeUpdate("INSERT INTO verify_codes VALUES('", encryptedEmail, "', '", code, "')");
		// 인증코드 insert or refresh
		
		Mail.sendMail(email, MailSubjects.VERIFY_SUBJECT.getName(), "코드 : ".concat(code));
		// 인증코드 전송
		
		result.setSuccess(true);
		return result;
	}

	@Override
	public OperationResult verifyEmail(String email, String code) {
		OperationResult result = new OperationResult();
		String encryptedEmail = aes.encrypt(email);

		rs = database.executeQuery("SELECT * FROM verify_codes WHERE email='", encryptedEmail, "' AND code='", code, "'");
		try {
			if (rs.next()) {
				database.executeUpdate("DELETE FROM verify_codes WHERE email='", encryptedEmail, "' AND code='", code, "'");
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public OperationResult checkIdExists(String id) {
		OperationResult result = new OperationResult();
		String encryptedId = aes.encrypt(id);

		rs = database.executeQuery("SELECT * FROM account WHERE id='", encryptedId, "'");
		try {
			if (rs.next()) {
				// 아이디 존재
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public void signup(String id, String email, String password) {
		String encryptedId = aes.encrypt(id);
		String encryptedEmail = aes.encrypt(email);
		String encryptedPassword = SHA256.encrypt(password);

		database.executeUpdate("INSERT INTO account(id, email, password) VALUES('", encryptedId, "', '", encryptedEmail, "', '", encryptedPassword, "')");
		Mail.sendMail(email, MailSubjects.WELCOME_SUBJECT.getName(), "환영환영");
	}

	@Override
	public OperationResult signin(String id, String password) {
		OperationResult result = new OperationResult();
		String encryptedId = aes.encrypt(id);
		String encryptedPassword = SHA256.encrypt(password);

		rs = database.executeQuery("SELECT * FROM account WHERE id='", encryptedId, "' AND password='",
				encryptedPassword, "'");
		try {
			if (rs.next()) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
			}

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			result.setSuccess(false);
			return result;
		}
	}

	@Override
	public String getIdFromSession(RoutingContext ctx) {
		return SessionUtil.getRegistedSessionKey(ctx, "UserSession");
	}
	
	@Override
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

	@Override
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

	@Override
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
	
	@Override
	public boolean isLogined(RoutingContext ctx) {
		return ((getIdFromSession(ctx) == null) ? false : true);
	}
}
