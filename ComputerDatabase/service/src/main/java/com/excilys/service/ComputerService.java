package com.excilys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;

@Service
public class ComputerService {

    @Autowired
    ComputerDAO dao;

    /* AbstractService */

    @Transactional
    public Computer find(long id) {
        return dao.findOne(id);
    }

    @Transactional
    public Iterable<Computer> findAll() {
        return dao.findAll();
    }

    @Transactional
    public Page<Computer> findPage(PageRequest pageRequest) {
        return dao.findAll(pageRequest);
    }

    // @Transactional
    public Computer insert(Computer entity) {
        return dao.save(entity);
    }

    @Transactional
    public Computer update(long id, Computer updateValue) {
        Computer toUpdate = dao.findOne(id);
        toUpdate.setName(updateValue.getName());
        toUpdate.setIntroduced(updateValue.getIntroduced());
        toUpdate.setDiscontinued(updateValue.getDiscontinued());
        toUpdate.setCompany(updateValue.getCompany());
        dao.save(toUpdate);
        return toUpdate;
    }

    @Transactional
    public long count() {
        return dao.count();
    }

    @Transactional
    public void remove(long id) {
        dao.delete(id);
    }

}
