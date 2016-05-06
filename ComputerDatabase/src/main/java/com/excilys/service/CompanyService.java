package com.excilys.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionManager;
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
        ConnectionManager.INSTANCE.initConnection();
        Company res = dao.find(id);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public List<Company> findAll() {
        ConnectionManager.INSTANCE.initConnection();
        List<Company> res = dao.findAll();
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public Page<Company> findPage(PageRequest pageRequest) {
        ConnectionManager.INSTANCE.initConnection();
        int number = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        Page<Company> res = new Page<>(number, (int) dao.count(pageRequest) / size, size, dao.findSeveral(pageRequest));
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public Company insert(Company entity) {
        ConnectionManager.INSTANCE.initConnection();
        Company res = dao.insert(entity);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public Company update(long id, Company updateValue) {
        ConnectionManager.INSTANCE.initConnection();
        Company res = dao.update(id, updateValue);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public long count() {
        ConnectionManager.INSTANCE.initConnection();
        long res = dao.count();
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public long count(PageRequest request) {
        ConnectionManager.INSTANCE.initConnection();
        long res = dao.count(request);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public void remove(long id) {
        ConnectionManager.INSTANCE.initTransaction();
        try {
            ComputerDAO.getInstance().removeCompanyId(id);
            CompanyDAO.getInstance().remove(id);
            ConnectionManager.INSTANCE.commit();
        } catch (DAOException e) {
            ConnectionManager.INSTANCE.rollBack();
        }
        ConnectionManager.INSTANCE.closeConnection();
    }

}
