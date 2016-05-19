package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionManager;
import com.excilys.persistence.DAOException;

@Service
public class CompanyService {

    @Autowired
    CompanyDAO cpnDao;

    @Autowired
    ComputerDAO cptDao;

    /**
     * Constructs a SimpleServices.
     * @param dao The dao that links the services with the database
     */
    public CompanyService() {
    }

    /* AbstractService */

    public Company find(long id) {
        ConnectionManager.INSTANCE.initConnection();
        Company res = cpnDao.find(id);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    public List<Company> findAll() {
        ConnectionManager.INSTANCE.initConnection();
        List<Company> res = cpnDao.findAll();
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    public Page<Company> findPage(PageRequest pageRequest) {
        ConnectionManager.INSTANCE.initConnection();
        int number = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        Page<Company> res = new Page<>(number, (int) cpnDao.count(pageRequest) / size, size,
                cpnDao.findSeveral(pageRequest));
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    public Company insert(Company entity) {
        ConnectionManager.INSTANCE.initConnection();
        Company res = cpnDao.insert(entity);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    public Company update(long id, Company updateValue) {
        ConnectionManager.INSTANCE.initConnection();
        Company res = cpnDao.update(id, updateValue);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    public long count() {
        ConnectionManager.INSTANCE.initConnection();
        long res = cpnDao.count();
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    public long count(PageRequest request) {
        ConnectionManager.INSTANCE.initConnection();
        long res = cpnDao.count(request);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    public void remove(long id) {
        ConnectionManager.INSTANCE.initTransaction();
        try {
            cptDao.removeCompanyId(id);
            cpnDao.remove(id);
            ConnectionManager.INSTANCE.commit();
        } catch (DAOException e) {
            ConnectionManager.INSTANCE.rollBack();
        }
        ConnectionManager.INSTANCE.closeConnection();
    }

}
