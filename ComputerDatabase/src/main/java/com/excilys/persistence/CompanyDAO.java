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

import com.excilys.mapper.DAOCompanyMapper;
import com.excilys.model.Company;
import com.excilys.model.PageRequest;

@Repository
public class CompanyDAO extends AbstractDAO<Company> {

    private static final String FIND_QUERY = "SELECT id, name FROM company WHERE id=?";

    private static final String FIND_ALL_QUERY = "SELECT id, name FROM company";

    private static final String FIND_SEVERAL_QUERY = "SELECT id, name FROM company WHERE name LIKE ? ORDER BY %s %s LIMIT ? OFFSET ?";

    private static final String INSERT_QUERY = "INSERT INTO company (name) VALUES (?)";

    private static final String DELETE_COMPANY_QUERY = "DELETE FROM company WHERE id=?";

    private static final String UPDATE_QUERY = "UPDATE company SET name=? WHERE id=?";

    private static final String COUNT_QUERY = "SELECT COUNT(id) FROM company";

    private Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    @Autowired
    private DAOCompanyMapper mapper;

    @Autowired
    @Qualifier("dataSource")
    private DataSource datasource;

    /* DAO Functionalities */

    @Override
    public Company find(long id) {
        logger.debug("<CompanyDAO> running find() with id = " + id);
        JdbcTemplate template = new JdbcTemplate(datasource);
        Object[] args = { id };
        try {
            return template.queryForObject(FIND_QUERY, args, (ResultSet result, int rowNum) -> mapper.unmap(result));
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(e);
        }
    }

    @Override
    public List<Company> findAll() {
        logger.debug("<CompanyDAO> running findAll()");
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.queryForObject(FIND_ALL_QUERY, (ResultSet results, int rowNum) -> {
            results.beforeFirst();
            ArrayList<Company> companies = new ArrayList<>();
            while (results.next()) {
                companies.add(mapper.unmap(results));
            }
            return companies;
        });
    }

    @Override
    public List<Company> findSeveral(PageRequest pageRequest) {
        logger.debug("<CompanyDAO> running findSeveral()");
        String query = String.format(FIND_SEVERAL_QUERY, pageRequest.getOrderByColumn(), pageRequest.getDirection());
        JdbcTemplate template = new JdbcTemplate(datasource);
        return template.queryForObject(query, (ResultSet results, int rowNum) -> {
            results.beforeFirst();
            ArrayList<Company> companies = new ArrayList<>();
            while (results.next()) {
                companies.add(mapper.unmap(results));
            }
            return companies;
        });
    }

    @Override
    public void remove(long id) {
        logger.debug("<CompanyDAO> running remove() with id = " + id);
        JdbcTemplate template = new JdbcTemplate(datasource);
        template.update(DELETE_COMPANY_QUERY, id);
    }

    @Override
    public Company insert(Company entity) {
        logger.debug("<CompanyDAO> running insert() with " + entity);
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
            return new Company(generatedKeyHolder.getKey().longValue(), entity.getName());
        }
    }

    @Override
    public Company update(long id, Company updateValue) {
        logger.debug("<CompanyDAO> running update() with id = " + id + " and updateValue = " + updateValue);
        JdbcTemplate template = new JdbcTemplate(datasource);
        template.update(UPDATE_QUERY, updateValue.getName(), id);
        return new Company(id, updateValue.getName());
    }

    @Override
    public long count() {
        logger.debug("<CompanyDAO> running count()");
        JdbcTemplate template = new JdbcTemplate(datasource);
        try {
            return template.queryForObject(COUNT_QUERY, (ResultSet results, int rowNum) -> results.getLong(1));
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(e);
        }
    }

    @Override
    public long count(PageRequest pageRequest) {
        // TODO : Not used yet
        return 0;
    }

}
