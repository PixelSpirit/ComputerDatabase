package com.excilys.test;

import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;

public class ComputerDAOTest {
	
	public static Computer freshComputer(){
		Computer entity = new Computer.Builder()
				.id(3)
				.name("LambdaY")
				.introduced(Timestamp.valueOf("2010-01-01 00:00:01.0"))
				.discontinued(Timestamp.valueOf("2016-01-01 00:00:01.0"))
				.companyId(new Long(42))
				.build();
		return entity;
	}
	
	public static void testFind(){
		ComputerDAO cdao = ComputerDAO.getInstance();
		try{
			Computer c = cdao.find(575);
			System.out.println(c);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void testInsert(){
		ComputerDAO cdao = ComputerDAO.getInstance();
		try{
			Computer c = freshComputer();
			Computer res = cdao.insert(c);
			System.out.println(res);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		testFind();
		testInsert();
	}
}
