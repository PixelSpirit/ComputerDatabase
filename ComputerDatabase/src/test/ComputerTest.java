package test;

import java.sql.Timestamp;

import model.Computer;

public class ComputerTest {
	
	public static void toStringTest(){
		Computer c = new Computer(0);
		c.setName("LambdaY");
		c.setIntroduced(Timestamp.valueOf("2010-01-01 00:00:01.0"));
		c.setDiscontinued(Timestamp.valueOf("2016-01-01 00:00:01.0"));
		c.setCompanyId(new Long(42));
		System.out.println(c);
	}
	
	public static void main(String[] args){
		toStringTest();
	}

}
