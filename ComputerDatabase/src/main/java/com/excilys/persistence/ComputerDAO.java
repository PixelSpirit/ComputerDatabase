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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.mapper.DAOComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.PageRequest;

@Repository
public class ComputerDAO extends AbstractDAO<Computer> {

    private static final String FIND_QUERY = "SELECT cptr.id, cptr.name, cptr.introduced, cptr.discontinued, cpn.id, cpn.name "
            + "FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id " + "WHERE cptr.id=?";

    private static final String FIND_ALL_QUERY = "SELECT cptr.id, cptr.name, cptr.introduced, cptr.discontinued, cpn.id, cpn.name "
            + "FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id";

    private static final String LIKE = "WHERE cptr.name LIKE ? OR cpn.name LIKE ?";

    private static final String ORDER_BY = "ORDER BY %s %s";

    private static final String LIMIT = "LIMIT ? OFFSET ?";

    private static final String INSERT_QUERY = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";

    private static final String DELETE_QUERY = "DELETE FROM computer WHERE id=?";

    private static final String DELETE_COMPANY_ID_QUERY = "DELETE FROM computer WHERE company_id = ?";

    private static final String UPDATE_QUERY = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?";

    private static final String COUNT_QUERY = "SELECT COUNT(id) FROM computer";

    private static final String COUNT_SEVERAL_QUERY = "SELECT COUNT(cptr.id) FROM computer AS cptr LEFT JOIN company AS cpn ON cptr.company_id = cpn.id "
            + "WHERE cptr.name LIKE ? OR cpn.name LIKE ?";

    private Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    @Autowired
    private DAOComputerMapper mapper;

    @Autowired
    private ConnectionManager connectionManager;

    @Override
    public Computer find(long id) {
        logger.debug("<ComptuerDAO> running find() with id = " + id);
        Connection connect = connectionManager.getConnection();
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
        logger.debug("<ComputerDAO> running findAll()");
        Connection connect = connectionManager.getConnection();
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

    public String createSeveralQuery(PageRequest pageRequest) {
        logger.debug("<ComputerDAO> running createSeveralQuery()");
        StringBuilder builder = new StringBuilder(FIND_ALL_QUERY);
        if (pageRequest.getSearch() != null) {
            builder.append(" ").append(LIKE);
        }
        if (pageRequest.getOrderByColumn() != null && pageRequest.getDirection() != null) {
            String order = String.format(ORDER_BY, pageRequest.getOrderByColumn().getSqlColumn(),
                    pageRequest.getDirection().getValue());
            builder.append(" ").append(order);
        }
        builder.append(" ").append(LIMIT);
        return builder.toString();
    }

    @Override
    public List<Computer> findSeveral(PageRequest pageRequest) {
        logger.debug("<ComputerDAO> running findSeveral()");
        Connection connect = connectionManager.getConnection();
        if (connect != null) {
            String query = createSeveralQuery(pageRequest);
            try (PreparedStatement stmt = connect.prepareStatement(query)) {
                int index = 1;
                String searchFormat = pageRequest.getSearch();
                if (searchFormat != null) {
                    String search = searchFormat + "%";
                    stmt.setString(index++, search);
                    stmt.setString(index++, search);
                }
                stmt.setInt(index++, pageRequest.getPageSize());
                stmt.setInt(index, pageRequest.getPageNumber() * pageRequest.getPageSize());
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
        logger.debug("<ComputerDAO> running remove() with id = " + id);
        Connection connect = connectionManager.getConnection();
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
        logger.debug("<ComputerDAO> running removeCompanyId() with companyId = " + companyId);
        Connection connect = connectionManager.getConnection();
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
        logger.debug("<ComputerDAO> running insert() with computer = " + entity);
        Connection connect = connectionManager.getConnection();
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
        logger.debug("<ComputerDAO> running update() with id = " + id + " and updateValue = " + updateValue);
        Connection connect = connectionManager.getConnection();
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
        logger.debug("<ComputerDAO> running count()");
        Connection connect = connectionManager.getConnection();
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
        logger.debug("<ComputerDAO> running count() with pageRequest");
        Connection connect = connectionManager.getConnection();
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
