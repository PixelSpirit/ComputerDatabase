package com.excilys.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionException;
import com.excilys.persistence.ConnectionFactory;
import com.excilys.persistence.DAOException;

public class CompanyService extends AbstractService<Company> {

    private Logger logger = LoggerFactory.getLogger(CompanyService.class);

    /**
     * The access to comapanie's DAO.
     */
    CompanyDAO dao = CompanyDAO.getInstance();

    /**
     * Constructs a SimpleServices.
     * @param dao The dao that links the services with the database
     */
    public CompanyService() {
    }

    /* AbstractService */

    @Override
    public Company find(long id) {
        return dao.find(id);
    }

    @Override
    public List<Company> findAll() {
        return dao.findAll();
    }

    @Override
    public Page<Company> findPage(PageRequest pageRequest) {
        int number = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        return new Page<>(number, (int) dao.count(pageRequest) / size, size, dao.findSeveral(pageRequest));
    }

    @Override
    public Company insert(Company entity) {
        return dao.insert(entity);
    }

    @Override
    public Company update(long id, Company updateValue) {
        return dao.update(id, updateValue);
    }

    @Override
    public long count() {
        return dao.count();
    }

    @Override
    public long count(PageRequest request) {
        return dao.count(request);
    }

    @Override
    public void remove(long id) {
        try (Connection connect = ConnectionFactory.INSTANCE.get()) {
            try {
                connect.setAutoCommit(false);
                ComputerDAO.getInstance().removeCompanyId(id, connect);
                CompanyDAO.getInstance().remove(id, connect);
                connect.commit();
            } catch (DAOException e) {
                connect.rollback();
            }
        } catch (ConnectionException | SQLException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            throw new DAOException(e);
        }
    }

}
