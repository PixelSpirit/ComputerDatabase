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
import com.excilys.model.PageRequest;

public class CompanyDAO extends AbstractDAO<Company> {

    private static final String FIND_QUERY = "SELECT id, name FROM company WHERE id=?";

    private static final String FIND_ALL_QUERY = "SELECT id, name FROM company";

    private static final String FIND_SEVERAL_QUERY = "SELECT id, name FROM company WHERE name LIKE ? ORDER BY %s %s LIMIT ? OFFSET ?";

    private static final String INSERT_QUERY = "INSERT INTO company (name) VALUES (?)";

    private static final String DELETE_COMPANY_QUERY = "DELETE FROM company WHERE id=?";

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
    public Company find(long id) {
        logger.debug("<CompanyDAO> running find() with id = " + id);
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(FIND_QUERY)) {
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
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }
    }

    @Override
    public List<Company> findAll() {
        logger.debug("<CompanyDAO> running findAll()");
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(FIND_ALL_QUERY)) {
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
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }
    }

    @Override
    public List<Company> findSeveral(PageRequest pageRequest) {
        logger.debug("<CompanyDAO> running findSeveral()");
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            // TODO : Update to match the new Query
            String query = String.format(FIND_SEVERAL_QUERY, pageRequest.getOrderByColumn(),
                    pageRequest.getDirection());
            try (PreparedStatement stmt = connect.prepareStatement(query)) {
                stmt.setInt(1, pageRequest.getPageNumber());
                stmt.setInt(2, pageRequest.getPageSize());
                ResultSet results = stmt.executeQuery();
                ArrayList<Company> companies = new ArrayList<>(pageRequest.getPageNumber());
                while (results.next()) {
                    companies.add(mapper.unmap(results));
                }
                return companies;
            } catch (SQLException e) {
                logger.error("[Catch] <SQLException> " + e.getMessage());
                throw new DAOException(e);
            }
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }

    }

    @Override
    public void remove(long id) {
        logger.debug("<CompanyDAO> running remove() with id = " + id);
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement removeCpn = connect.prepareStatement(DELETE_COMPANY_QUERY)) {
                removeCpn.setLong(1, id);
                removeCpn.executeUpdate();
            } catch (SQLException e) {
                logger.error("[Catch] <SQLException> " + e.getMessage());
                throw new DAOException(e);
            }
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }
    }

    @Override
    public Company insert(Company entity) {
        logger.debug("<CompanyDAO> running insert() with " + entity);
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
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
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }
    }

    @Override
    public Company update(long id, Company updateValue) {
        logger.debug("<CompanyDAO> running update() with id = " + id + " and updateValue = " + updateValue);
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                mapper.map(updateValue, stmt);
                stmt.setLong(2, id);
                stmt.executeUpdate();
                updateValue.setId(id);
                return updateValue;
            } catch (SQLException e) {
                logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
                throw new DAOException(e);
            }
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }
    }

    @Override
    public long count() {
        logger.debug("<CompanyDAO> running count()");
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(COUNT_QUERY)) {
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
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }
    }

    @Override
    public long count(PageRequest pageRequest) {
        // TODO : Remove if useless
        logger.debug("<CompanyDAO> running count() with pageRequest");
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            return 0;
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }
    }

}
