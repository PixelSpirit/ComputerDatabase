package com.excilys.service;

import java.util.List;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.persistence.ComputerDAO;

public class ComputerService extends AbstractService<Computer> {

    /**
     * The access to comapanie's DAO.
     */
    ComputerDAO dao = ComputerDAO.getInstance();

    /**
     * Constructs a SimpleServices.
     * @param dao The dao that links the services with the database
     */
    public ComputerService() {
    }

    /* AbstractService */

    @Override
    public Computer find(long id) {
        return dao.find(id);
    }

    @Override
    public List<Computer> findAll() {
        return dao.findAll();
    }

    @Override
    public Page<Computer> findPage(PageRequest pageRequest) {
        int number = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        return new Page<>(number, (int) dao.count(pageRequest) / size, size, dao.findSeveral(pageRequest));
    }

    @Override
    public Computer insert(Computer entity) {
        return dao.insert(entity);
    }

    @Override
    public Computer update(long id, Computer updateValue) {
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
        dao.remove(id);
    }

}
