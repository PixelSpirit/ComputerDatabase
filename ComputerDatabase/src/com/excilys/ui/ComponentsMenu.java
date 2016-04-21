package com.excilys.ui;

import java.util.InputMismatchException;

import com.excilys.service.CompanyServices;

public class ComponentsMenu extends Menu {
		
	int size;
	int pageNumber;


	/* Singleton */

	private static ComponentsMenu _instance;

	private ComponentsMenu(){
		super(" Components Menu");
		size = 10;
		pageNumber = 0;
	}

	synchronized public static ComponentsMenu getInstance(){
		if(_instance == null){
			_instance = new ComponentsMenu();
		}
		return _instance;
	}


	/* Menu */

	@Override
	protected void printContent() {
		CompanyServices.getInstance().printCompanies(pageNumber * size, size);
	}

	@Override
	protected void printOptions() {
		System.out.println("Page " + pageNumber + " :");
		System.out.println(" 0 - Print next companies");
		System.out.println(" 1 - Print previous companies");
		System.out.println(" 2 - Return to Main Menu");
	}

	@Override
	protected void selectOption() {

		boolean isValid = false;
		while(!isValid){
			try{
				switch(scanner.nextInt()){
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
