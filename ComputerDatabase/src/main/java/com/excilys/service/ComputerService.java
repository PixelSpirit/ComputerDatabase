package com.excilys.service;

import java.util.List;

import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.PageRequest;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.ConnectionManager;

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
        ConnectionManager.INSTANCE.initConnection();
        Computer res = dao.find(id);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public List<Computer> findAll() {
        ConnectionManager.INSTANCE.initConnection();
        List<Computer> res = dao.findAll();
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public Page<Computer> findPage(PageRequest pageRequest) {
        ConnectionManager.INSTANCE.initConnection();
        int number = pageRequest.getPageNumber();
        int size = pageRequest.getPageSize();
        Page<Computer> res = new Page<>(number, (int) dao.count(pageRequest) / size, size,
                dao.findSeveral(pageRequest));
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public Computer insert(Computer entity) {
        ConnectionManager.INSTANCE.initConnection();
        Computer res = dao.insert(entity);
        ConnectionManager.INSTANCE.closeConnection();
        return res;
    }

    @Override
    public Computer update(long id, Computer updateValue) {
        ConnectionManager.INSTANCE.initConnection();
        Computer res = dao.update(id, updateValue);
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
        ConnectionManager.INSTANCE.initConnection();
        dao.remove(id);
        ConnectionManager.INSTANCE.closeConnection();
    }

}
