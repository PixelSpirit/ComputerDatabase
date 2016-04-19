package persistence;

import java.sql.SQLException;

import javax.sql.RowSet;
import javax.sql.rowset.JdbcRowSet;

import com.sun.rowset.JdbcRowSetImpl;

public class Database {
	
	private static String user = "admincdb";
	private static String password = "qwerty1234";
	private static String url = "jdbc:mysql://localhost:3306/computer-database-db";
	
	public static void initRowSet(RowSet rowset) throws SQLException {
		rowset.setUrl(url);
		rowset.setUsername(user);
		rowset.setPassword(password);
	}
	
	public static RowSet getFreshRowSet() throws SQLException {
		JdbcRowSet rowset = new JdbcRowSetImpl();
		initRowSet(rowset);
		return rowset;
	}
}
