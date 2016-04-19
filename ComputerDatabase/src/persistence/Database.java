package persistence;

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;

import com.sun.rowset.JdbcRowSetImpl;

public class Database {
	
	private static final String USER = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";
	
	public static void initRowSet(RowSet rowset) throws SQLException {
		rowset.setUrl(URL);
		rowset.setUsername(USER);
		rowset.setPassword(PASSWORD);
	}
	
	public static JdbcRowSet getFreshRowSet() throws SQLException {
		JdbcRowSet rowset = new JdbcRowSetImpl();
		initRowSet(rowset);
		return rowset;
	}
}
