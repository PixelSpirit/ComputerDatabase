package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gives an access to the computer database
 */
public class ConnectionFactory {
	
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	
	/**
	 * Returns a fresh connection to the database
	 * @return a fresh connection to the database
	 * @throws SQLException if the database has encounter an error
	 */
	public static Connection get() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
