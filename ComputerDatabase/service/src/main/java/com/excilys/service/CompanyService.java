package com.excilys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;

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

    @Transactional
    public Company find(long id) {
        return cpnDao.findOne(id);
    }

    @Transactional
    public Iterable<Company> findAll() {
        return cpnDao.findAll();
    }

    @Transactional
    public Page<Company> findPage(PageRequest pageRequest) {
        return cpnDao.findAll(pageRequest);
    }

    @Transactional
    public Company insert(Company entity) {
        return cpnDao.save(entity);
    }

    @Transactional
    public Company update(long id, Company updateValue) {
        Company toUpdate = cpnDao.findOne(id);
        toUpdate.setName(updateValue.getName());
        return cpnDao.save(toUpdate);
    }

    @Transactional
    public long count() {
        return cpnDao.count();
    }

    @Transactional
    public void remove(long id) {
        cptDao.deleteByCompany_id(id);
        cpnDao.delete(id);
    }

}
