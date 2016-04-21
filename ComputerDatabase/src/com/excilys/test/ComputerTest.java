package com.excilys.test;

import java.sql.Timestamp;

import com.excilys.model.Computer;

public class ComputerTest {
	
	public static void toStringTest(){
		Computer entity = new Computer.Builder()
				.id(3)
				.name("LambdaY")
				.introduced(Timestamp.valueOf("2010-01-01 00:00:01.0"))
				.discontinued(Timestamp.valueOf("2016-01-01 00:00:01.0"))
				.companyId(new Long(42))
				.build();
		System.out.println(entity);
	}
	
	public static void main(String[] args){
		toStringTest();
	}

}
