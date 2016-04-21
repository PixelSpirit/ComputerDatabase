package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import mapper.CompanyMapper;
import mapper.ComputerMapper;
import model.Company;
import model.Computer;

public class CompanyDAO extends DAO<Company>{

	private static final String FIND_QUERY =
			"SELECT id, name FROM company WHERE id=?";
	
	private static final String FIND_ALL_QUERY =
			"SELECT id, name FROM company LIMIT ? OFFSET ?";

	private static final String INSERT_QUERY =
			"INSERT INTO company (name) VALUES (?)";

	private static final String DELETE_QUERY =
			"DELETE FROM company WHERE id=?";

	private static final String UPDATE_QUERY =
			"UPDATE company SET name=? WHERE id=?";

	
	/* Singleton */
	
	private static CompanyDAO _instance = null;
	
	private CompanyDAO() {
		super();
	}
	
	synchronized public static CompanyDAO getInstance(){
		if(_instance == null){
			_instance = new CompanyDAO();
		}
		return _instance;
	}
	
	
	
	/* DAO Functionalities */

	@Override
	public Company find(long id) throws SQLException {
		try(Connection connect = Database.getFreshConnection()){
			try(PreparedStatement stmt = connect.prepareStatement(FIND_QUERY)){
				stmt.setLong(1, id);
				ResultSet result = stmt.executeQuery();
				result.first();
				return CompanyMapper.getInstance().unmap(result);
			}
		}
	}

	@Override
	public LinkedList<Company> findSeveral(int n, int offset) throws SQLException {
		try(Connection connect = Database.getFreshConnection()){
			try(PreparedStatement stmt = connect.prepareStatement(FIND_ALL_QUERY)){
				stmt.setInt(1, n);
				stmt.setInt(2, offset);
				ResultSet results = stmt.executeQuery();
				LinkedList<Company> companies = new LinkedList<>();
				while(results.next()){
					companies.add(CompanyMapper.getInstance().unmap(results));
				}
				return companies;
			}
		}
	}


	@Override
	public void remove(long id) throws SQLException {
		try(Connection connect = Database.getFreshConnection()){
			try(PreparedStatement stmt = connect.prepareStatement(DELETE_QUERY)){
				stmt.setLong(1, id);
				stmt.executeUpdate();
			}
		}
	}

	@Override
	public Company insert(Company entity) throws SQLException {
		try(Connection connect = Database.getFreshConnection()){
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
	}

	@Override
	public Company update(long id, Company updateValue) throws SQLException {
		try(Connection connect = Database.getFreshConnection()){
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




}
