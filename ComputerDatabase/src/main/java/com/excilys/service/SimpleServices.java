package com.excilys.service;

import java.util.List;

import com.excilys.model.Page;
import com.excilys.persistence.AbstractDAO;

/**
 * A service that gives direct access to DAO.
 */
public class SimpleServices<T> extends AbstractService<T> {

    /**
     * The access to comapanie's DAO.
     */
    AbstractDAO<T> dao;

    /**
     * Constructs a SimpleServices.
     * @param dao The dao that links the services with the database
     */
    public SimpleServices(AbstractDAO<T> dao) {
        this.dao = dao;
    }

    /* AbstractService */

    @Override
    public T find(long id) {
        return dao.find(id);
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }

    @Override
    public Page<T> findPage(int number, int size) {
        return new Page<>(number, (int) dao.count() / size, size, dao.findSeveral(size, number * size));
    }

    @Override
    public void remove(long id) {
        dao.remove(id);
    }

    @Override
    public T insert(T entity) {
        return dao.insert(entity);
    }

    @Override
    public T update(long id, T updateValue) {
        return dao.update(id, updateValue);
    }

    @Override
    public long count() {
        return dao.count();
    }

}
