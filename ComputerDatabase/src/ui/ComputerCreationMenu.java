package ui;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

import model.Computer;
import service.ComputerServices;

public class ComputerCreationMenu extends Menu {

	/* Singleton */

	private static ComputerCreationMenu _instance;

	private ComputerCreationMenu(){
		super(" Computer Creation");
	}

	synchronized public static ComputerCreationMenu getInstance(){
		if(_instance == null){
			_instance = new ComputerCreationMenu();
		}
		return _instance;
	}


	/* Menu */

	private static String createName(){
		System.out.print("Enter Computer name > ");
		return scanner.nextLine();
	}
	
	private static Timestamp createDate(String title) {
		Timestamp stamp = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		while(stamp == null){
			try{
				System.out.print("Enter the " + title + " date > ");
				String format = scanner.nextLine();
				LocalDateTime date = LocalDateTime.parse(format, formatter);
				stamp = Timestamp.valueOf(date);
			} catch (DateTimeParseException e){
				//TODO : log
				System.err.println("Invalid date format");
			}
		}
		return stamp;
	}
	
	private static Long createCompanyId(){
		Long id = null;
		while(id == null){
			try{
				System.out.print("Enter Company's id > ");
				id = Long.parseLong(scanner.nextLine());
				//TODO : Check if the ID is valid !
				//TODO : It must be better with a name !
			} catch(InputMismatchException e){
				System.err.println("Invalid date format");
			}
		}
		return id;
	}

	@Override
	protected void printContent() {
		Computer computer = new Computer(-1);
		computer.setName(createName());
		Timestamp introduced;
		Timestamp discontinued;
		boolean firstIteration = true;
		do{
			introduced = createDate("introducing");
			discontinued = createDate("discontinuing");
			if(!firstIteration)
				System.err.println("Introducing data must be before the discountinuing date !");
			firstIteration = false;
		} while(introduced.after(discontinued));
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompanyId(createCompanyId());
		ComputerServices.getInstance().addNewComputer(computer);
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
			catch(InputMismatchException e){
				printInvalidCommand();
			}
		}
	}

}
