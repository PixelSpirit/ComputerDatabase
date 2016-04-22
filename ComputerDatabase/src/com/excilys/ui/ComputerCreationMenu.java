package com.excilys.ui;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.service.CompanyServices;
import com.excilys.service.ComputerServices;

public class ComputerCreationMenu extends Menu {

	/* Singleton */

	private static ComputerCreationMenu _instance;

	private ComputerCreationMenu(){
		super(" Computer Creation");
	}

	public static ComputerCreationMenu getInstance(){
		if(_instance == null){
			synchronized (ComputerCreationMenu.class) {
				if(_instance == null){
					_instance = new ComputerCreationMenu();
				}
			}
		}
		return _instance;
	}


	/* Menu */

	private static String createName(){
		System.out.print("Enter Computer name > ");
		return scanner.nextLine();
	}
	
	private static LocalDateTime createDate(String title) {
		LocalDateTime date = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		while(date == null){
			try{
				System.out.print("Enter the " + title + " date > ");
				String format = scanner.nextLine();
				date = LocalDateTime.parse(format, formatter);
			} catch (DateTimeParseException e){
				//TODO : log
				System.err.println("Invalid date format");
			}
		}
		return date;
	}
	

	private static Company createCompany(){
		Company company = null;
		while(company == null){
			try{
				System.out.print("Enter Company's id > ");
				Long id = Long.parseLong(scanner.nextLine());
				company = CompanyServices.getInstance().find(id);
				//TODO : Check if the ID is valid !
			} catch(InputMismatchException e){
				System.err.println("Invalid date format");
			} catch (SQLException e) {
				System.err.println("Can't acces database");
			}
		}
		return company;
	}
	
	private static Computer createComputer(){
		String name = createName();
		LocalDateTime introduced;
		LocalDateTime discontinued;
		boolean firstIteration = true;
		do{
			introduced = createDate("introducing");
			discontinued = createDate("discontinuing");
			if(!firstIteration)
				System.err.println("Introducing data must be before the discountinuing date !");
			firstIteration = false;
		} while(introduced.isAfter(discontinued));
		Company company = createCompany();
		return new Computer.Builder()
			.name(name)
			.introduced(introduced)
			.discontinued(discontinued)
			.company(company)
			.build();
	}

	@Override
	protected void printContent() {
		ComputerServices.getInstance().addNewComputer(createComputer());
		System.out.println("Computer was succefully added");
	}

	@Override
	protected void printOptions() {
		System.out.println(" 1 - Return to Computers Menu");
		System.out.println(" 2 - Return to Main Menu");
	}

	@Override
	protected void selectOption() {

		boolean isValid = false;
		while(!isValid){
			try{
				switch(Integer.parseInt(scanner.nextLine())){
				case 1:
					MenuNavigator.getInstance().setNextMenu(ComputersMenu.getInstance());
					isValid = true;
					break;
				case 2:
					MenuNavigator.getInstance().setNextMenu(MainMenu.getInstance());
					isValid = true;
					break;
				default:
					printInvalidCommand();
				}
			}
			catch(NumberFormatException e){
				printInvalidCommand();
			}
		}
	}

}