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

import com.excilys.mapper.DAOComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.PageRequest;

public class ComputerDAO extends AbstractDAO<Computer> {

    private static final String FIND_QUERY = "SELECT cptr.id, cptr.name, cptr.introduced, cptr.discontinued, cpn.id, cpn.name "
            + "FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id " + "WHERE cptr.id=?";

    private static final String FIND_ALL_QUERY = "SELECT cptr.id, cptr.name, cptr.introduced, cptr.discontinued, cpn.id, cpn.name "
            + "FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id ";

    private static final String FIND_SEVERAL_QUERY = "SELECT cptr.id, cptr.name, cptr.introduced, cptr.discontinued, cpn.id, cpn.name "
            + "FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id WHERE cptr.name LIKE ? OR cpn.name LIKE ? "
            + "ORDER BY %s %s LIMIT ? OFFSET ?";

    private static final String INSERT_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";

    private static final String DELETE_QUERY = "DELETE FROM computer WHERE id=?";

    private static final String DELETE_COMPANY_ID_QUERY = "DELETE FROM computer WHERE compan_id = ?";

    private static final String UPDATE_QUERY = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

    private static final String COUNT_QUERY = "SELECT COUNT(id) FROM computer";

    private static final String COUNT_SEVERAL_QUERY = "SELECT COUNT(cptr.id) FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id "
            + "WHERE cptr.name LIKE ? OR cpn.name LIKE ?";

    private Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    private DAOComputerMapper mapper = DAOComputerMapper.getInstance();

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
    public Computer find(long id) {
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(FIND_QUERY)) {
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
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }
    }

    @Override
    public List<Computer> findAll() {
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(FIND_ALL_QUERY)) {
                ResultSet results = stmt.executeQuery();
                ArrayList<Computer> computers = new ArrayList<>();
                while (results.next()) {
                    computers.add(mapper.unmap(results));
                }
                return computers;
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
    public List<Computer> findSeveral(PageRequest pageRequest) {
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            String query = String.format(FIND_SEVERAL_QUERY, pageRequest.getOrderByColumn().getSqlColumn(),
                    pageRequest.isAscendent() ? "ASC" : "DESC");
            try (PreparedStatement stmt = connect.prepareStatement(query)) {
                stmt.setString(1, "%" + ((pageRequest.getSearch() != null) ? pageRequest.getSearch() : "") + "%");
                stmt.setString(2, "%" + ((pageRequest.getSearch() != null) ? pageRequest.getSearch() : "") + "%");
                stmt.setInt(3, pageRequest.getPageSize());
                stmt.setInt(4, pageRequest.getPageNumber() * pageRequest.getPageSize());
                ResultSet results = stmt.executeQuery();
                ArrayList<Computer> computers = new ArrayList<>(pageRequest.getPageSize());
                while (results.next()) {
                    computers.add(mapper.unmap(results));
                }
                return computers;
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
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(DELETE_QUERY)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                logger.error("[Catch] <SQLException> " + e.getMessage());
                throw new DAOException(e);
            }
        } else {
            logger.error("Connection was not initialized");
            throw new DAOException("Connection was not initialized");
        }
    }

    public void removeCompanyId(long companyId) {
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(DELETE_COMPANY_ID_QUERY)) {
                stmt.setLong(1, companyId);
                stmt.executeUpdate();
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
    public Computer insert(Computer entity) {
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
    public Computer update(long id, Computer updateValue) {
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            try (PreparedStatement stmt = connect.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
                mapper.map(updateValue, stmt);
                stmt.setLong(5, id);
                stmt.executeUpdate();
                updateValue.setId(id);
                return updateValue;
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
    public long count() {
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
        Connection connect = ConnectionManager.INSTANCE.getConnection();
        if (connect != null) {
            String like = "cptr.name";
            String query = String.format(COUNT_SEVERAL_QUERY, like);
            try (PreparedStatement stmt = connect.prepareStatement(query)) {
                stmt.setString(1, "%" + ((pageRequest.getSearch() != null) ? pageRequest.getSearch() : "") + "%");
                stmt.setString(2, "%" + ((pageRequest.getSearch() != null) ? pageRequest.getSearch() : "") + "%");
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

}
