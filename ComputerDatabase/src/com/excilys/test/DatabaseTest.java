package com.excilys.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.persistence.Database;

public class DatabaseTest {
	
	static public void connectionTest(){
		try (Connection connect = Database.getFreshConnection()){
			try (PreparedStatement stmt = connect.prepareStatement("SELECT * FROM computer")){
				ResultSet res = stmt.executeQuery();
				while(res.next()){
					System.out.println(res.getLong("id"));
				}
			}
		}
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args){
		connectionTest();
	}
	
}
