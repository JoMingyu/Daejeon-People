package com.planb.support.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	private static Connection connection;
	private static Statement statement;
	
	private final String URL = "jdbc:mysql://localhost:3306/";
	private final String USER = "root";
	private final String PASSWORD = "uursty199";
	
	private DataBase() {
		// 2개 이상의 오브젝트가 동시에 DB 액세스 시 문제 발생.
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL + "daejeon_people", USER, PASSWORD);
			statement = connection.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static String buildQuery(Object... args) {
		StringBuilder query = new StringBuilder();
		
		for(Object o: args) {
			query.append(o);
		}
		
		return query.toString();
	}
	
	public static ResultSet executeQuery(Object... args) {
		String query = buildQuery(args);
		try {
			return statement.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int executeUpdate(Object... args) {
		String query = buildQuery(args);
		try {
			return statement.executeUpdate(query);
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
