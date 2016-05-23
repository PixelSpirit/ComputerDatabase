package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.model.Company;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionManager;

@Service
public class CompanyService {

    @Autowired
    ConnectionManager connectionManager;

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

    @Transactional
    public Company find(long id) {
        return cpnDao.find(id);
    }

    @Transactional
    public List<Company> findAll() {
        return cpnDao.findAll();
    }

    @Transactional
    public Page<Company> findPage(PageRequest pageRequest) {
        int number = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        return new Page<>(number, (int) cpnDao.count(pageRequest) / size, size, cpnDao.findSeveral(pageRequest));
    }

    @Transactional
    public Company insert(Company entity) {
        return cpnDao.insert(entity);
    }

    @Transactional
    public Company update(long id, Company updateValue) {
        return cpnDao.update(id, updateValue);
    }

    @Transactional
    public long count() {
        return cpnDao.count();
    }

    @Transactional
    public long count(PageRequest request) {
        return cpnDao.count(request);
    }

    @Transactional
    public void remove(long id) {
        cptDao.removeCompanyId(id);
        cpnDao.remove(id);
    }

}
