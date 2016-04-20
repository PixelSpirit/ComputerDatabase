package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		return null;
		// TODO
	}

	@Override
	public Computer insert(Computer entity) throws SQLException {
		try(PreparedStatement stmt = connect.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)){
			ComputerMapper.getInstance().map(entity, stmt);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.first()){
				return find(rs.getLong(1));
			}
			else{
				throw new SQLException("No key was found");
			}
		}
	}

	@Override
	public Computer update(long id, Computer updateValue) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
		

}
