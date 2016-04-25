package com.excilys.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.excilys.model.Computer;

public class ComputerTest {
	
	public static void toStringTest(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Computer entity = new Computer.Builder()
				.id(3)
				.name("LambdaY")
				.introduced(LocalDateTime.parse("2010-01-01 00:00:01.0", formatter))
				.discontinued(LocalDateTime.parse("2016-01-01 00:00:01.0", formatter))
				.build();
		System.out.println(entity);
	}
	
	public static void main(String[] args){
		toStringTest();
	}

}
