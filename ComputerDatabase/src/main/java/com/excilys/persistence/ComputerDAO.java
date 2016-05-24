package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
    @Qualifier("dataSource")
    private DataSource datasource;

    @Override
    public Computer find(long id) {
        logger.debug("<ComptuerDAO> running find() with id = " + id);
        JdbcTemplate template = new JdbcTemplate(datasource);
        Object[] args = { id };
        try {
            return template.queryForObject(FIND_QUERY, args, (ResultSet result, int rowNum) -> mapper.unmap(result));
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(e);
        }
    }

    @Override
    public List<Computer> findAll() {
        logger.debug("<ComputerDAO> running findAll()");
        JdbcTemplate template = new JdbcTemplate(datasource);
        try {
            return template.queryForObject(FIND_ALL_QUERY, (ResultSet results, int rowNum) -> {
                ArrayList<Computer> computers = new ArrayList<>();
                results.beforeFirst();
                while (results.next()) {
                    computers.add(mapper.unmap(results));
                }
                return computers;
            });
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
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
        String query = createSeveralQuery(pageRequest);
        JdbcTemplate template = new JdbcTemplate(datasource);
        Object[] args;
        int size = pageRequest.getPageSize();
        int offset = pageRequest.getPageNumber() * pageRequest.getPageSize();

        if (pageRequest.getSearch() != null) {
            String search = pageRequest.getSearch() + "%";
            args = new Object[] { search, search, size, offset };
        } else {
            args = new Object[] { size, offset };
        }
        try {
            return template.queryForObject(query, args, (ResultSet results, int rowNum) -> {
                results.beforeFirst();
                ArrayList<Computer> computers = new ArrayList<>(pageRequest.getPageSize());
                while (results.next()) {
                    computers.add(mapper.unmap(results));
                }
                return computers;
            });
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void remove(long id) {
        logger.debug("<ComputerDAO> running remove() with id = " + id);
        JdbcTemplate template = new JdbcTemplate(datasource);
        template.update(DELETE_QUERY, id);
    }

    public void removeCompanyId(long companyId) {
        logger.debug("<ComputerDAO> running removeCompanyId() with companyId = " + companyId);
        JdbcTemplate template = new JdbcTemplate(datasource);
        template.update(DELETE_COMPANY_ID_QUERY, companyId);
    }

    @Override
    public Computer insert(Computer entity) {
        logger.debug("<ComputerDAO> running insert() with computer = " + entity);
        JdbcTemplate template = new JdbcTemplate(datasource);
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        int affectedRows = template.update((Connection con) -> {
            PreparedStatement stmt = con.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            mapper.map(entity, stmt);
            return stmt;
        } , generatedKeyHolder);
        if (affectedRows == 0) {
            throw new DAOException("Insertion failed");
        } else {
            Computer cpt = new Computer(entity);
            cpt.setId(generatedKeyHolder.getKey().longValue());
            return cpt;
        }
    }

    @Override
    public Computer update(long id, Computer updateValue) {
        logger.debug("<ComputerDAO> running update() with id = " + id + " and updateValue = " + updateValue);
        JdbcTemplate template = new JdbcTemplate(datasource);
        template.update((Connection con) -> {
            PreparedStatement stmt = con.prepareStatement(UPDATE_QUERY);
            mapper.map(updateValue, stmt);
            stmt.setLong(5, id);
            return stmt;
        });
        Computer cpt = new Computer(updateValue);
        cpt.setId(id);
        return cpt;
    }

    @Override
    public long count() {
        logger.debug("<ComputerDAO> running count()");
        JdbcTemplate template = new JdbcTemplate(datasource);
        try {
            return template.queryForObject(COUNT_QUERY, (ResultSet results, int rowNum) -> results.getLong(1));
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(e);
        }
    }

    @Override
    public long count(PageRequest pageRequest) {
        logger.debug("<ComputerDAO> running count() with pageRequest");
        JdbcTemplate template = new JdbcTemplate(datasource);
        Object[] args;
        String search = pageRequest.getSearch();
        if (search != null) {
            args = new Object[] { search + "%", search + "%" };
        } else {
            args = new Object[] { "%", "%" };
        }
        try {
            return template.queryForObject(COUNT_SEVERAL_QUERY, args,
                    (ResultSet results, int numRow) -> results.getLong(1));
        } catch (EmptyResultDataAccessException e) {
            throw new DAOException("No count result");
        }
    }
}
