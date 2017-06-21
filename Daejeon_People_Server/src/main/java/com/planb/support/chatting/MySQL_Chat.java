package com.planb.support.chatting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.planb.support.utilities.Config;
import com.planb.support.utilities.Log;

public class MySQL_Chat {
	private static Connection connection;
	
	private static String url;
	private static String user = Config.getValue("dbUserName");
	private static String password = Config.getValue("dbPassword");
	
	static {
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append("jdbc:mysql://localhost:");
		urlBuilder.append(Config.getValue("dbPort")).append("/");
		urlBuilder.append(Config.getValue("dbTableName2")).append("?");
		urlBuilder.append("useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
		
		url = urlBuilder.toString();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized static PreparedStatement buildQuery(String sql, Object... args) {
		Log.Q(sql);
		
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			int placeholderCount = 1;
			for(Object o: args) {
				statement.setObject(placeholderCount++, o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return statement;
	}
	
	public synchronized static ResultSet executeQuery(String sql, Object... args) {
		try {
			return buildQuery(sql, args).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public synchronized static int executeUpdate(String sql, Object... args) {
		try {
			return buildQuery(sql, args).executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}