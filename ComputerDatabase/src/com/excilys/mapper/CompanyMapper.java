package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.model.Company;


public class CompanyMapper implements Mapper<Company> {
	
	private static final String ID = "id";
	private static final String NAME = "name";
	
	/* Singleton */

	private static CompanyMapper _instance = null;
	
	private CompanyMapper() {
		super();
	}
	
	public static CompanyMapper getInstance() {
		if(_instance == null){
			synchronized (CompanyMapper.class) {
				if(_instance == null){
					_instance = new CompanyMapper();
				}
			}
		}
		return _instance;
	}
	
	
	/* Mapper */
	
	@Override
	public void map(Company entity, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, entity.getName());
	}

	@Override
	public Company unmap(ResultSet databaseRow) throws SQLException {
		Company entity = new Company(databaseRow.getLong(ID));
		entity.setName(databaseRow.getString(NAME));
		return entity;
	}

	
}
