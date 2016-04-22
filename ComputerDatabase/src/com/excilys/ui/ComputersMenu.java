package com.excilys.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.service.ComputerServices;
import com.excilys.service.ServiceException;

public class ComputersMenu extends Menu {
		
	private int size;
	private int pageNumber;

	private Logger logger = LoggerFactory.getLogger(ComputersMenu.class);
	
	/* Singleton */
	
	private static ComputersMenu _instance;
	
	private ComputersMenu(){
		super(" Computers Menu");
		size = 10;
		pageNumber = 0;
	}
	
	public static ComputersMenu getInstance(){
		if(_instance == null){
			synchronized (ComputersMenu.class) {
				if(_instance == null){
					_instance = new ComputersMenu();
				}
			}
		}
		return _instance;
	}

	
	/* Menu */
	
	@Override
	protected void printContent() {
		try {
			ComputerServices.getInstance().printComputers(pageNumber * size, size);
		} catch (ServiceException e) {
			logger.error("[Catch] <ServiceException>");
			System.err.println("Computers can not be printed...");
		}
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
			catch(NumberFormatException e){
				printInvalidCommand();
			}
		}
	}
}
