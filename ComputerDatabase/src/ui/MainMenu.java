package ui;

import java.util.InputMismatchException;

public class MainMenu extends Menu {
	
	/* Singleton */
	
	private static MainMenu _instance;
	
	private MainMenu(){
		super(" Main Menu");
	}
	
	synchronized public static MainMenu getInstance(){
		if(_instance == null){
			_instance = new MainMenu();
		}
		return _instance;
	}
	
	
	/* Menu */
	
	@Override
	protected void printContent() {}

	@Override
	protected void printOptions() {
		System.out.println(" 0 - Print computers");
		System.out.println(" 1 - Print companies");
		System.out.println(" 2 - Quit");
	}

	@Override
	protected void selectOption() {

		boolean isValid = false;
		while(!isValid){
			try{
				switch(Integer.parseInt(scanner.nextLine())){
				case 0:
					MenuNavigator.getInstance().setNextMenu(ComputersMenu.getInstance());
					isValid = true;
					break;
				case 1:
					MenuNavigator.getInstance().setNextMenu(ComponentsMenu.getInstance());
					isValid = true;
					break;
				case 2:
					System.exit(0);
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
