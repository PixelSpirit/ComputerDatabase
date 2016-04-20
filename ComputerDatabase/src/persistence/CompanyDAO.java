package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mapper.CompanyMapper;
import model.Company;

public class CompanyDAO extends DAO<Company>{
	
	private static final String FIND_QUERY =
		"SELECT id, name FROM company WHERE id=?";
		
	private static final String INSERT_QUERY =
		"INSERT INTO company (name) VALUES (?)";
		
	private static final String DELETE_QUERY =
		"DELETE FROM company WHERE id=?";
		
	private static final String UPDATE_QUERY =
		"UPDATE company SET name=? WHERE id=?";

	public CompanyDAO() throws SQLException {
		super();
	}


	@Override
	public Company find(long id) throws SQLException {
		try(PreparedStatement stmt = connect.prepareStatement(FIND_QUERY)){
			stmt.setLong(1, id);
			ResultSet result = stmt.executeQuery();
			result.first();
			return CompanyMapper.getInstance().unmap(result);
		}
	}

	@Override
	public void remove(long id) throws SQLException {
		try(PreparedStatement stmt = connect.prepareStatement(DELETE_QUERY)){
			stmt.setLong(1, id);
			stmt.executeUpdate();
		}
	}

	@Override
	public Company insert(Company entity) throws SQLException {
		try(PreparedStatement stmt = connect.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)){
			CompanyMapper.getInstance().map(entity, stmt);
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
	public Company update(long id, Company updateValue) throws SQLException {
		try(PreparedStatement stmt = connect.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)){
			CompanyMapper.getInstance().map(updateValue, stmt);
			stmt.setLong(2, id);
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
	
	

}
