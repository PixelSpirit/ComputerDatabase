package com.excilys.ui;

import java.util.InputMismatchException;

import com.excilys.service.ComputerServices;

public class ComputersMenu extends Menu {
		
	int size;
	int pageNumber;

	
	/* Singleton */
	
	private static ComputersMenu _instance;
	
	private ComputersMenu(){
		super(" Computers Menu");
		size = 10;
		pageNumber = 0;
	}
	
	synchronized public static ComputersMenu getInstance(){
		if(_instance == null){
			_instance = new ComputersMenu();
		}
		return _instance;
	}

	
	/* Menu */
	
	@Override
	protected void printContent() {
		ComputerServices.getInstance().printComputers(pageNumber * size, size);
	}

	@Override
	protected void printOptions() {
		System.out.println("Page " + pageNumber + " :");
		System.out.println(" 0 - Print next computers");
		System.out.println(" 1 - Print previous computers");
		System.out.println(" 2 - Add a computer");
		System.out.println(" 3 - Update a computer");
		System.out.println(" 4 - Return to Main Menu");
	}

	@Override
	protected void selectOption() {

		boolean isValid = false;
		while(!isValid){
			try{
				switch(Integer.parseInt(scanner.nextLine())){
				case 0:
					//TODO check that we are not at the limit !
					pageNumber++;
					isValid = true;
					break;
				case 1:
					if(pageNumber > 0)
						pageNumber--;
					isValid = true;
					break;
				case 2:
					MenuNavigator.getInstance().setNextMenu(ComputerCreationMenu.getInstance());
					isValid = true;
					break;
				case 3:
					//TODO
					System.err.println("Not implemented yet !");
					isValid = true;
					break;
				case 4:
					MenuNavigator.getInstance().setNextMenu(MainMenu.getInstance());
					isValid = true;
					break;
				default:
					printInvalidCommand();
				}
			}
			catch(InputMismatchException e){
				printInvalidCommand();
			}
		}
	}
}
