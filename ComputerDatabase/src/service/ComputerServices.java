package service;

import java.sql.SQLException;
import java.util.LinkedList;

import model.Computer;
import persistence.ComputerDAO;
import persistence.DAO;

public class ComputerServices {
	
	DAO<Computer> dao = ComputerDAO.getInstance();
	
	/* Singleton */
	
	private static ComputerServices _instance;
	
	private ComputerServices(){}
	
	synchronized public static ComputerServices getInstance(){
		if(_instance == null){
			_instance = new ComputerServices();
		}
		return _instance;
	}
	
	
	/* Functionalities */
	
	public void printComputers(int from, int size){
		LinkedList<Computer> computers;
		try {
			computers = dao.findSeveral(size, from);
			System.out.println("Computers :");
			
			for (Computer computer : computers) {
				System.out.println(computer);
			}
		} catch (SQLException e) {
			//TODO Log error
			System.err.println("Database communication error : Computer can't be printed");
		}
	}
	
	public void addNewComputer(Computer c){
		try {
			dao.insert(c);
		} catch (SQLException e) {
			//TODO Log error
			System.err.println("Database communication error : Computer can't be added");
		}
	}
	
	public void updateComputer(long id, Computer freshValue){
		try{
			dao.update(id, freshValue);
		} catch (SQLException e){
			//TODO Log error
			System.err.println("Database communication error : Computer can't be updated");
		}
	}

}
