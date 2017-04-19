package com.planb.support.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.crypto.AES256;
import com.planb.support.crypto.SHA256;
import com.planb.support.database.DataBase;

import io.vertx.ext.web.RoutingContext;

public class UserManager implements AccountManageable {
	private DataBase database = DataBase.getInstance();
	private AES256 aes = new AES256("d.df!*&ek@s.Cde/q");
	private ResultSet rs;
	
	@Override
	public OperationResult certifyEmail(RoutingContext context, String email) {
		OperationResult result = new OperationResult();
		String encryptedEmail = aes.encrypt(email);
		
		rs = database.executeQuery("SELECT * FROM account WHERE email='", encryptedEmail, "'");
		try {
			if(rs.next()) {
				result.setSuccess(false);
				result.setMessage("이미 사용중인 이메일입니다.");
			} else {
				// 이메일 인증
				result.setSuccess(true);
			}
			
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage("error");
			
			return result;
		}
	}
	
	@Override
	public boolean checkIdExists(String id) {
		String encryptedId = aes.encrypt(id);
		
		rs = database.executeQuery("SELECT * FROM account WHERE id='", encryptedId, "'");
		try {
			if(rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public OperationResult register(String id, String email, String password) {
		OperationResult result = new OperationResult();
		String encryptedId = aes.encrypt(id);
		String encryptedEmail = aes.encrypt(email);
		String encryptedPassword = SHA256.encrypt(password);
		
		return result;
	}
	
	@Override
	public OperationResult login(String id, String password) {
		OperationResult result = new OperationResult();
		
		return result;
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
