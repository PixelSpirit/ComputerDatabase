package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionManager;

@Service
public class ComputerService {

    @Autowired
    ConnectionManager connectionManager;

    @Autowired
    ComputerDAO dao;

    /**
     * Constructs a SimpleServices.
     * @param dao The dao that links the services with the database
     */
    public ComputerService() {
    }

    /* AbstractService */

    @Transactional
    public Computer find(long id) {
        return dao.find(id);
    }

    @Transactional
    public List<Computer> findAll() {
        List<Computer> res = dao.findAll();
        return res;
    }

    @Transactional
    public Page<Computer> findPage(PageRequest pageRequest) {
        int number = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        Page<Computer> res = new Page<>(number, (int) dao.count(pageRequest) / size, size,
                dao.findSeveral(pageRequest));
        return res;
    }

    @Transactional
    public Computer insert(Computer entity) {
        Computer res = dao.insert(entity);
        return res;
    }

    @Transactional
    public Computer update(long id, Computer updateValue) {
        Computer res = dao.update(id, updateValue);
        return res;
    }

    @Transactional
    public long count() {
        long res = dao.count();
        return res;
    }

    @Transactional
    public long count(PageRequest request) {
        long res = dao.count(request);
        return res;
    }

    @Transactional
    public void remove(long id) {
        dao.remove(id);
    }

}
