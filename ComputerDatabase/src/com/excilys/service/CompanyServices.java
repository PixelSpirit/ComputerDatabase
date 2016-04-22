package com.excilys.service;

import java.sql.SQLException;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.DAO;

/**
 * Groups the different services to use companies
 */
public class CompanyServices {
	
	/**
	 * The access to comapanie's DAO
	 */
	private DAO<Company> dao = CompanyDAO.getInstance();
	
	private Logger logger = LoggerFactory.getLogger(CompanyServices.class);
	
	/* Singleton */
	
	private static CompanyServices _instance;
	
	private CompanyServices(){}
	
	synchronized public static CompanyServices getInstance(){
		if(_instance == null){
			_instance = new CompanyServices();
		}
		return _instance;
	}
	
	
	/* Functionalities */
	
	/**
	 * Prints the size companies from the from-th company found by the DAO
	 * @param from The number of the first company to print
	 * @param size The number of companies to print
	 */
	public void printCompanies(int from, int size){
		LinkedList<Company> companies;
		try {
			companies = dao.findSeveral(size, from);
			System.out.println("Companies :");
			
			for (Company company : companies) {
				System.out.println(company);
			}
		} catch (SQLException e) {
			logger.error(e.getSQLState());
			System.err.println("Database communication error : Companies can't be printed");
		}
	}
	
	public Company find(long id) throws SQLException {
		return dao.find(id);
	}
}
