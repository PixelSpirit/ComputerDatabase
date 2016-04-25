package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.excilys.model.Company;
import com.excilys.model.Computer;



public class ComputerMapper implements DAOMappable<Computer> {
	
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
	
	public static ComputerMapper getInstance() {
		if(_instance == null){
			synchronized (ComputerMapper.class) {
				if(_instance == null){
					_instance = new ComputerMapper();
				}
			}
		}
		return _instance;
	}

	
	/* Mapper */
	
	@Override
	public void map(Computer entity, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, entity.getName());
		stmt.setTimestamp(2, Timestamp.valueOf(entity.getIntroduced()));
		stmt.setTimestamp(3, Timestamp.valueOf(entity.getDiscontinued()));
		if (entity.getCompany() != null) {
			stmt.setLong(4, entity.getCompany().getId());
		}
		else {
			stmt.setNull(4, java.sql.Types.BIGINT);  
		}
	}

	@Override
	public Computer unmap(ResultSet databaseRow) throws SQLException {
		String introduced = databaseRow.getString(INTRODUCED);
		String discontinued = databaseRow.getString(DISCONTINUED);
		
		Company cpn = new Company(
				databaseRow.getLong(COMPANY_ID),
				databaseRow.getString(COMPANY_NAME)
				);
		Computer cpt = new Computer.Builder()
			.id(databaseRow.getLong(ID))
			.name(databaseRow.getString(NAME))
			.introduced((introduced != null && introduced != "0000-00-00 00:00:00.0") ? LocalDateTime.parse(introduced, Computer.formatter) : null)
			.discontinued((discontinued != null && discontinued != "0000-00-00 00:00:00.0") ? LocalDateTime.parse(discontinued, Computer.formatter) : null)
			.company(cpn)
			.build();
		return cpt;
	}

}
