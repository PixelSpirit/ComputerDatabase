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

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;

public class ComputerDAO extends AbstractDAO<Computer> {

    private static final String FIND_QUERY = "SELECT cptr.id, cptr.name, cptr.introduced, cptr.discontinued, cpn.id, cpn.name "
            + "FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id " + "WHERE cptr.id=?";

    private static final String FIND_ALL_QUERY = "SELECT cptr.id, cptr.name, cptr.introduced, cptr.discontinued, cpn.id, cpn.name "
            + "FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id " + "LIMIT ? OFFSET ?";

    private static final String INSERT_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";

    private static final String DELETE_QUERY = "DELETE FROM computer WHERE id=?";

    private static final String UPDATE_QUERY = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

    private static final String COUNT_QUERY = "SELECT COUNT(id) FROM computer";

    private Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    private ComputerMapper mapper = ComputerMapper.getInstance();

    /* Singleton */

    private static ComputerDAO instance = null;

    /**
     * Constructs a ComputerDAO.
     */
    private ComputerDAO() {
        super();
    }

    /**
     * @return The unique instance of a ComputerDAO.
     */
    public static ComputerDAO getInstance() {
        if (instance == null) {
            synchronized (ComputerDAO.class) {
                if (instance == null) {
                    instance = new ComputerDAO();
                }
            }
        }
        return instance;
    }

    /* DAO Functionalities */

    @Override
    public Computer find(long id) throws ConnectionException, DAOException, NotFoundException {
        try (Connection connect = ConnectionFactory.get();
                PreparedStatement stmt = connect.prepareStatement(FIND_QUERY)) {
            stmt.setLong(1, id);
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
    public List<Computer> findSeveral(int n, int offset) throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.get();
                PreparedStatement stmt = connect.prepareStatement(FIND_ALL_QUERY)) {
            stmt.setInt(1, n);
            stmt.setInt(2, offset);
            ResultSet results = stmt.executeQuery();
            ArrayList<Computer> computers = new ArrayList<>(n);
            while (results.next()) {
                computers.add(mapper.unmap(results));
            }
            return computers;
        } catch (SQLException e) {
            logger.error("[Catch] <SQLException> " + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void remove(long id) throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.get();
                PreparedStatement stmt = connect.prepareStatement(DELETE_QUERY)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("[Catch] <SQLException> " + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public Computer insert(Computer entity) throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.get();
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
    public Computer update(long id, Computer updateValue) throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.get();
                PreparedStatement stmt = connect.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            mapper.map(updateValue, stmt);
            stmt.setLong(5, id);
            stmt.executeUpdate();
            updateValue.setId(id);
            return updateValue;
        } catch (SQLException e) {
            logger.error("[Catch] <SQLException> " + e.getMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public long count() throws ConnectionException, DAOException {
        try (Connection connect = ConnectionFactory.get();
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
