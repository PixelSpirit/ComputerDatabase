package service;

import java.sql.SQLException;
import java.util.LinkedList;

import model.Company;
import persistence.CompanyDAO;
import persistence.DAO;

public class CompanyServices {
	
	DAO<Company> dao = CompanyDAO.getInstance();
	
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
	
	public void printCompanies(int from, int size){
		LinkedList<Company> companies;
		try {
			companies = dao.findSeveral(size, from);
			System.out.println("Companies :");
			
			for (Company company : companies) {
				System.out.println(company);
			}
		} catch (SQLException e) {
			//TODO Log error
			System.err.println("Database communication error : Companies can't be printed");
		}
	}
}
