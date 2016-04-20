package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	
	public static Connection getFreshConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
