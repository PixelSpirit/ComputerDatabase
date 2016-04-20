package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.validation.Schema;

import mapper.ComputerMapper;
import model.Computer;

public class ComputerDAO extends DAO<Computer> {

	private static final String FIND_QUERY =
		"SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=?";
	
	private static final String INSERT_QUERY =
		"INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	
	public ComputerDAO() throws SQLException {
		super();
	}
	
	
	/* DAO Functionalities */

	@Override
	public Computer find(long id) throws SQLException {
		try(PreparedStatement stmt = connect.prepareStatement(FIND_QUERY)){
			stmt.setLong(1, id);
			ResultSet result = stmt.executeQuery();
			result.first();
			return ComputerMapper.getInstance().unmap(result);
		}
	}

	@Override
	public Computer remove(long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Computer entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(long id, Computer updateValue) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

}
