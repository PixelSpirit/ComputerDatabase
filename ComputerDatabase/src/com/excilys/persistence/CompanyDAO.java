package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;

public class CompanyDAO extends AbstractDAO<Company>{

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
	
	
	private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private CompanyMapper mapper = CompanyMapper.getInstance();
	
	/* Singleton */
	
	private static CompanyDAO _instance = null;
	
	private CompanyDAO() {
		super();
	}
	
	public static CompanyDAO getInstance(){
		if(_instance == null){
			synchronized (CompanyDAO.class) {
				if(_instance == null){
					_instance = new CompanyDAO();
				}
			}
		}
		return _instance;
	}
	
	
	
	/* DAO Functionalities */

	@Override
	public Company find(long id) throws ConnectionException, DAOException {
		try(Connection connect = ConnectionFactory.get();
				PreparedStatement stmt = connect.prepareStatement(FIND_QUERY)){
			stmt.setLong(1, id);
			logger.info("<SQL Query> Selecting company where id = " + id);
			ResultSet result = stmt.executeQuery();
			result.first();
			return mapper.unmap(result); 
		} catch (SQLException e) {
			logger.error("[Catch] <SQLException> " + e.getMessage());
			logger.warn("[Throw] <DAOException>");
			throw new DAOException(e);
		} 
	}

	@Override
	public List<Company> findSeveral(int n, int offset) throws ConnectionException, DAOException {
		try(Connection connect = ConnectionFactory.get();
				PreparedStatement stmt = connect.prepareStatement(FIND_ALL_QUERY)){
			stmt.setInt(1, n);
			stmt.setInt(2, offset);
			ResultSet results = stmt.executeQuery();
			ArrayList<Company> companies = new ArrayList<>(n);
			while(results.next()){
				companies.add(mapper.unmap(results));
			}
			return companies;
		} catch (SQLException e) {
			logger.error("[Catch] <SQLException> " + e.getMessage());
			logger.warn("[Throw] <DAOException>");
			throw new DAOException(e);
		}
	}


	@Override
	public void remove(long id) throws ConnectionException, DAOException {
		try(Connection connect = ConnectionFactory.get();
				PreparedStatement stmt = connect.prepareStatement(DELETE_QUERY)){
			stmt.setLong(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("[Catch] <SQLException> " + e.getMessage());
			logger.warn("[Throw] <DAOException>");
			throw new DAOException(e);
		}
	}

	@Override
	public Company insert(Company entity) throws ConnectionException, DAOException {
		try(Connection connect = ConnectionFactory.get();
				PreparedStatement stmt = connect.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)){
			mapper.map(entity, stmt);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.first()){
				return find(rs.getLong(1));
			}
			else{
				throw new SQLException("No key was found");
			}
		} catch (SQLException e) {
			logger.error("[Catch] <SQLException> " + e.getMessage());
			logger.warn("[Throw] <DAOException>");
			throw new DAOException(e);
		}
	}

	@Override
	public Company update(long id, Company updateValue) throws  ConnectionException, DAOException {
		try(Connection connect = ConnectionFactory.get();
				PreparedStatement stmt = connect.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)){
			mapper.map(updateValue, stmt);
			stmt.setLong(2, id);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.first()){
				return find(rs.getLong(1));
			}
			else{
				throw new SQLException("No key was found");

			} 
		} catch (SQLException e) {
			logger.error("[Catch] <SQLException> " + e.getMessage());
			logger.warn("[Throw] <DAOException>");
			throw new DAOException(e);
		}
	}




}
