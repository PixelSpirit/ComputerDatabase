package com.excilys.service;

import java.sql.SQLException;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.DAO;

/**
 * Groups the different services to use computers
 */
public class ComputerServices {
	
	/**
	 * The access to comapanie's DAO
	 */
	DAO<Computer> dao = ComputerDAO.getInstance();
	
	private Logger logger = LoggerFactory.getLogger(ComputerServices.class);
	
	/* Singleton */
	
	private static ComputerServices _instance;
	
	private ComputerServices(){}
	
	public static ComputerServices getInstance(){
		if(_instance == null){
			synchronized (ComputerServices.class) {
				if(_instance == null){
					_instance = new ComputerServices();
				}
			}
		}
		return _instance;
	}
	
	
	/* Functionalities */
	
	/**
	 * Prints the size computers from the from-th company found by the DAO
	 * @param from The number of the first computer to print
	 * @param size The number of computers to print
	 */
	public void printComputers(int from, int size){
		LinkedList<Computer> computers;
		try {
			computers = dao.findSeveral(size, from);
			System.out.println("Computers :");
			
			for (Computer computer : computers) {
				System.out.println(computer);
			}
		} catch (SQLException e) {
			logger.error(e.getSQLState());
			System.err.println("Database communication error : Computer can't be printed");
		}
	}
	
	public void addNewComputer(Computer c){
		try {
			dao.insert(c);
		} catch (SQLException e) {
			logger.error(e.getSQLState());
			System.err.println("Database communication error : Computer can't be added");
		}
	}
	
	public void updateComputer(long id, Computer freshValue){
		try{
			dao.update(id, freshValue);
		} catch (SQLException e){
			logger.error(e.getSQLState());
			System.err.println("Database communication error : Computer can't be updated");
		}
	}

}
