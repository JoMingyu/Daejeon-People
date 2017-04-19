package com.planb.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.planb.support.crypto.AES256;
import com.planb.support.crypto.SHA256;
import com.planb.support.database.DataBase;
import com.sun.javafx.binding.StringFormatter;

import io.vertx.ext.web.RoutingContext;

public class UserManager implements AccountManageable {
	private DataBase database = DataBase.getInstance();
	private AES256 aes = new AES256("d.df!*&ek@s.Cde/q");
	private ResultSet rs;
	
	@Override
	public OperationResult certifyEmail(String email) {
		OperationResult result = new OperationResult();
		String encryptedEmail = aes.encrypt(email);
		
		rs = database.executeQuery("SELECT * FROM account WHERE email='", encryptedEmail, "'");
		try {
			if(rs.next()) {
				result.setSuccess(false);
			} else {
				// 이메일 인증
				Random random = new Random();
				String code = StringFormatter.format("%06d", random.nextInt(1000000)).getValue();
				database.executeUpdate("DELETE FROM certify_codes WHERE email='", encryptedEmail, "'");
				database.executeUpdate("INSERT INTO certify_codes VALUES('", email, "', '", code, "')");
				
				result.setSuccess(true);
			}
			
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			result.setSuccess(false);
			return result;
		}
	}
	
	@Override
	public OperationResult verifyEmail(String email, String code) {
		OperationResult result = new OperationResult();
		String encryptedEmail = aes.encrypt(email);
		
		rs = database.executeQuery("SELECT * FROM certify_codes WHERE email='", encryptedEmail, "', code='", code, "'");
		try {
			if(rs.next()) {
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
			if(rs.next()) {
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
	public void register(String id, String email, String password) {
		String encryptedId = aes.encrypt(id);
		String encryptedEmail = aes.encrypt(email);
		String encryptedPassword = SHA256.encrypt(password);
		
		database.executeUpdate("INSERT INTO account(id, email, password) VALUES('", encryptedId, "', '", encryptedEmail, "', '", encryptedPassword, "')");
	}
	
	@Override
	public OperationResult login(String id, String password) {
		OperationResult result = new OperationResult();
		String encryptedId = aes.encrypt(id);
		String encryptedPassword = SHA256.encrypt(password);
		
		rs = database.executeQuery("SELECT * FROM account WHERE id='", encryptedId, "' AND password='", encryptedPassword, "'");
		try {
			if(rs.next()) {
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
	public String getIdFromSession(RoutingContext context) {
		return null;
	}

	@Override
	public String createSession() {
		return null;
	}

	@Override
	public String registerSessionId(RoutingContext context, String id) {
		return null;
	}
}
