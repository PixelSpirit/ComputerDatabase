package com.excilys.ui;

import java.util.Scanner;

public abstract class Menu {

	protected String title = null;
	protected static Scanner scanner = new Scanner(System.in);
	
	public Menu(String title){
		this.title = title;
	}
	
	public void runMenu(){
		printTitle();
		System.out.println();
		printContent();
		System.out.println();
		printOptions();
		System.out.println();
		System.out.print("% > ");
		selectOption();
	}
	
	private void printTitle(){
		System.out.println("***********************");
		System.out.println(title);
		System.out.println("***********************");
	}
	
	protected void printInvalidCommand(){
		System.err.println("Invalid command !");
		System.out.print("% > ");
	}
	
	protected abstract void printContent();
	
	protected abstract void printOptions();
	
	protected abstract void selectOption();
	
}
