package com.excilys.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.service.CompanyServices;
import com.excilys.service.ServiceException;

public class ComponentsMenu extends Menu {
		
	private int size;
	private int pageNumber;

	private Logger logger = LoggerFactory.getLogger(ComponentsMenu.class);

	/* Singleton */

	private static ComponentsMenu _instance;

	private ComponentsMenu(){
		super(" Components Menu");
		size = 10;
		pageNumber = 0;
	}

	public static ComponentsMenu getInstance(){
		if(_instance == null){
			synchronized (ComponentsMenu.class) {
				if(_instance == null){
					_instance = new ComponentsMenu();
				}
			}
		}
		return _instance;
	}


	/* Menu */

	@Override
	protected void printContent() {
		try {
			CompanyServices.getInstance().printCompanies(pageNumber * size, size);
		} catch (ServiceException e) {
			logger.error("[Catch] <ServiceException>");
			System.err.println("Companies can not be printed...");
		}
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
			catch(NumberFormatException e){
				printInvalidCommand();
			}
		}
	}

}
