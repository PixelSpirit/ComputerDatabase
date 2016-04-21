package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.model.Company;
import com.excilys.model.Computer;



public class ComputerMapper implements Mapper<Computer> {
	
	private static final String ID = "cptr.id";
	private static final String NAME = "cptr.name";
	private static final String INTRODUCED = "cptr.introduced";
	private static final String DISCONTINUED = "cptr.discontinued";
	private static final String COMPANY_ID = "cpn.id";
	private static final String COMPANY_NAME = "cpn.name";
	
	
	/* Singleton */
	
	private static ComputerMapper _instance = null;
	
	private ComputerMapper() {
		super();
	}
	
	synchronized public static ComputerMapper getInstance() {
		if(_instance == null) {
			_instance = new ComputerMapper();
		}
		return _instance;
	}

	
	/* Mapper */
	
	@Override
	public void map(Computer entity, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, entity.getName());
		stmt.setTimestamp(2, entity.getIntroduced());
		stmt.setTimestamp(3, entity.getDiscontinued());
		stmt.setLong(4, entity.getCompany().getId());
		
	}

	@Override
	public Computer unmap(ResultSet databaseRow) throws SQLException {
		Company cpn = new Company(
				databaseRow.getLong(COMPANY_ID),
				databaseRow.getString(COMPANY_NAME)
				);
		Computer cpt = new Computer.Builder()
			.id(databaseRow.getLong(ID))
			.name(databaseRow.getString(NAME))
			.introduced(databaseRow.getTimestamp(INTRODUCED))
			.discontinued(databaseRow.getTimestamp(DISCONTINUED))
			.company(cpn)
			.build();
		return cpt;
	}

}
