package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.DAOCompanyMapper;
import com.excilys.model.Company;

public class CompanyDAO extends AbstractDAO<Company> {

    private static final String FIND_QUERY = "SELECT id, name FROM company WHERE id=?";

    private static final String FIND_ALL_QUERY = "SELECT id, name FROM company";

    private static final String FIND_SEVERAL_QUERY = "SELECT id, name FROM company LIMIT ? OFFSET ?";

    private static final String INSERT_QUERY = "INSERT INTO company (name) VALUES (?)";

    private static final String DELETE_QUERY = "DELETE FROM company WHERE id=?";

    private static final String UPDATE_QUERY = "UPDATE company SET name=? WHERE id=?";

    private static final String COUNT_QUERY = "SELECT COUNT(id) FROM company";

    private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private DAOCompanyMapper mapper = DAOCompanyMapper.getInstance();

    /* Singleton */

    private static CompanyDAO instance = null;

    /**
     * Constrcuts a CompanyDAO.
     */
    private CompanyDAO() {
        super();
    }

    /**
     * @return The unique instance of a CompanyDAO.
     */
    public static CompanyDAO getInstance() {
        if (instance == null) {
            synchronized (CompanyDAO.class) {
                if (instance == null) {
                    instance = new CompanyDAO();
                }
            }
        }
        return instance;
    }

    /* DAO Functionalities */

    @Override
    public Company find(long id) throws ConnectionException, DAOException, NotFoundException {
        try (Connection connect = ConnectionFactory.INSTANCE.get();
                PreparedStatement stmt = connect.prepareStatement(FIND_QUERY)) {
            stmt.setLong(1, id);
            logger.info("<SQL Query> Selecting company where id = " + id);
            ResultSet result = stmt.executeQuery();
            if (result.first()) {
                return mapper.unmap(result);
            } else {
                throw new NotFoundException();
            }
        } catch (SQLException e) {
            logger.error("[Catch] <SQLException> " + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> findAll() throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.INSTANCE.get();
                PreparedStatement stmt = connect.prepareStatement(FIND_ALL_QUERY)) {
            ResultSet results = stmt.executeQuery();
            ArrayList<Company> companies = new ArrayList<>();
            while (results.next()) {
                companies.add(mapper.unmap(results));
            }
            return companies;
        } catch (SQLException e) {
            logger.error("[Catch] <SQLException> " + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public List<Company> findSeveral(int n, int offset) throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.INSTANCE.get();
                PreparedStatement stmt = connect.prepareStatement(FIND_SEVERAL_QUERY)) {
            stmt.setInt(1, n);
            stmt.setInt(2, offset);
            ResultSet results = stmt.executeQuery();
            ArrayList<Company> companies = new ArrayList<>(n);
            while (results.next()) {
                companies.add(mapper.unmap(results));
            }
            return companies;
        } catch (SQLException e) {
            logger.error("[Catch] <SQLException> " + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void remove(long id) throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.INSTANCE.get();
                PreparedStatement stmt = connect.prepareStatement(DELETE_QUERY)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("[Catch] <SQLException> " + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Company insert(Company entity) throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.INSTANCE.get();
                PreparedStatement stmt = connect.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            mapper.map(entity, stmt);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.first()) {
                return find(rs.getLong(1));
            } else {
                throw new DAOException("Insertion failed");
            }
        } catch (SQLException | NotFoundException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Company update(long id, Company updateValue) throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.INSTANCE.get();
                PreparedStatement stmt = connect.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            mapper.map(updateValue, stmt);
            stmt.setLong(2, id);
            stmt.executeUpdate();
            updateValue.setId(id);
            return updateValue;
        } catch (SQLException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public long count() throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.INSTANCE.get();
                PreparedStatement stmt = connect.prepareStatement(COUNT_QUERY)) {
            ResultSet results = stmt.executeQuery();
            if (results.first()) {
                return results.getLong(1);
            } else {
                throw new DAOException("No count result");
            }
        } catch (SQLException e) {
            logger.error("[Catch] <SQLException> " + e.getMessage());
            throw new DAOException(e);
        }
    }

}
