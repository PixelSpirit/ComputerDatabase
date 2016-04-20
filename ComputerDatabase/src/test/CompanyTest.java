package test;

import model.Company;

public class CompanyTest {
	
	public static void toStringTest(){
		Company c = new Company(0);
		c.setName("Constructor");
		System.out.println(c);
	}
	
	public static void main(String[] args){
		toStringTest();
	}

}
