package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import com.excilys.mapper.CompanyMapper;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;

public class ComputerDAO extends DAO<Computer> {

	private static final String FIND_QUERY =
			"SELECT cptr.id, cptr.name, cptr.introduced, cptr.discontinued, cpn.id, cpn.name "
			+ "FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id "
			+ "WHERE cptr.id=?";

	private static final String FIND_ALL_QUERY =
			"SELECT cptr.id, cptr.name, cptr.introduced, cptr.discontinued, cpn.id, cpn.name "
			+ "FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id "
			+ "LIMIT ? OFFSET ?";

	private static final String INSERT_QUERY =
			"INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";

	private static final String DELETE_QUERY =
			"DELETE FROM computer WHERE id=?";

	private static final String UPDATE_QUERY =
			"UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

	
	
	private ComputerMapper mapper = ComputerMapper.getInstance();

	/* Singleton */
	
	private static ComputerDAO _instance = null;
	
	private ComputerDAO() {
		super();
	}
	
	public static ComputerDAO getInstance(){
		if(_instance == null){
			synchronized (ComputerDAO.class) {
				if(_instance == null){
					_instance = new ComputerDAO();
				}
			}
		}
		return _instance;
	}


	/* DAO Functionalities */

	@Override
	public Computer find(long id) throws SQLException {
		try(Connection connect = ConnectionFactory.get()){
			try(PreparedStatement stmt = connect.prepareStatement(FIND_QUERY)){
				stmt.setLong(1, id);
				ResultSet result = stmt.executeQuery();
				result.first();
				return mapper.unmap(result);
			}
		}
	}

	@Override
	public LinkedList<Computer> findSeveral(int n, int offset) throws SQLException {
		try(Connection connect = ConnectionFactory.get()){
			try(PreparedStatement stmt = connect.prepareStatement(FIND_ALL_QUERY)){
				stmt.setInt(1, n);
				stmt.setInt(2, offset);
				ResultSet results = stmt.executeQuery();
				LinkedList<Computer> computers = new LinkedList<>();
				while(results.next()){
					computers.add(mapper.unmap(results));
				}
				return computers;
			}
		}
	}


	@Override
	public void remove(long id) throws SQLException {
		try(Connection connect = ConnectionFactory.get()){
			try(PreparedStatement stmt = connect.prepareStatement(DELETE_QUERY)){
				stmt.setLong(1, id);
				stmt.executeUpdate();
			}
		}
	}

	@Override
	public Computer insert(Computer entity) throws SQLException {
		try(Connection connect = ConnectionFactory.get()){
			try(PreparedStatement stmt = connect.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)){
				mapper.map(entity, stmt);
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
	public Computer update(long id, Computer updateValue) throws SQLException {
		try(Connection connect = ConnectionFactory.get()){
			try(PreparedStatement stmt = connect.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)){
				mapper.map(updateValue, stmt);
				stmt.setLong(5, id);
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
