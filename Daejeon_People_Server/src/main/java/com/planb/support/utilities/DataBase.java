package com.planb.support.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	private static Connection connection;
	
	private static final String URL = "jdbc:mysql://localhost:3306/";
	private static final String USER = "root";
	private static final String PASSWORD = "uursty199";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL + "daejeon_people", USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static PreparedStatement buildQuery(String sql, Object... args) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);
			
			int placeholderCount = 1;
			for(Object o: args) {
				if(o.getClass() == java.lang.String.class) {
					statement.setString(placeholderCount++, o.toString());
				} else if(o.getClass() == java.lang.Integer.class) {
					statement.setInt(placeholderCount++, Integer.parseInt(o.toString()));
				} else if(o.getClass() == java.lang.Double.class) {
					statement.setDouble(placeholderCount++, Double.parseDouble(o.toString()));
				} else if(o.getClass() == java.lang.Float.class) {
					statement.setFloat(placeholderCount++, Float.parseFloat(o.toString()));
				} else if(o.getClass() == java.lang.Boolean.class) {
					statement.setBoolean(placeholderCount++, Boolean.parseBoolean(o.toString()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return statement;
	}
	
	public static ResultSet executeQuery(String sql, Object... args) {
		PreparedStatement statement = buildQuery(sql, args);
		try {
			return statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int executeUpdate(String sql, Object... args) {
		PreparedStatement statement = buildQuery(sql, args);
		try {
			return statement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
