package com.planb.support.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
	private static Connection connection;
	
	private static final String URL = "jdbc:mysql://localhost:3306/daejeon_people?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "uursty199";
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static PreparedStatement buildQuery(String sql, Object... args) {
		Log.Q(sql);
		
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			int placeholderCount = 1;
			for(Object o : args) {
				statement.setObject(placeholderCount++, o);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return statement;
	}
	
	public static ResultSet executeQuery(String sql, Object... args) {
		try {
			return buildQuery(sql, args).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int executeUpdate(String sql, Object... args) {
		try {
			return buildQuery(sql, args).executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
