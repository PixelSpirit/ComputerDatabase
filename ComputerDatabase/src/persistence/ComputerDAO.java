package persistence;

import java.sql.SQLException;

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
		rowset.setCommand(FIND_QUERY);
		rowset.setLong(1, id);
		rowset.execute();
		rowset.first();
		return ComputerMapper.getInstance().unmap(rowset);
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
