package com.excilys.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;

public class ComputerDAOTest {
	
	public static Computer freshComputer(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Computer entity = new Computer.Builder()
				.id(3)
				.name("LambdaY")
				.introduced(LocalDateTime.parse("2010-01-01 00:00:01.0", formatter))
				.discontinued(LocalDateTime.parse("2016-01-01 00:00:01.0", formatter))
				.build();
		return entity;
	}
	
	public static void testFind(){
		ComputerDAO cdao = ComputerDAO.getInstance();
		try{
			Computer c = cdao.find(575);
			System.out.println(c);
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
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		testFind();
		testInsert();
	}
}
