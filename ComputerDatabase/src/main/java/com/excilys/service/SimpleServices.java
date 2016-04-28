package com.excilys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Page;
import com.excilys.persistence.AbstractDAO;
import com.excilys.persistence.ConnectionException;
import com.excilys.persistence.DAOException;
import com.excilys.persistence.NotFoundException;

/**
 * A service that gives direct access to DAO.
 */
public class SimpleServices<T> extends AbstractService<T> {

    /**
     * The access to comapanie's DAO.
     */
    AbstractDAO<T> dao;

    private Logger logger = LoggerFactory.getLogger(SimpleServices.class);

    /**
     * Constructs a SimpleServices.
     * @param dao The dao that links the services with the database
     */
    public SimpleServices(AbstractDAO<T> dao) {
        this.dao = dao;
    }

    /* AbstractService */

    @Override
    public T find(long id) throws ServiceException, NotFoundException {
        try {
            return dao.find(id);
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            throw new ServiceException(e);
        }
    }

    @Override
    public Page<T> findPage(int number, int size) throws ServiceException {
        try {
            return new Page<>(number, (int) dao.count() / size, size, dao.findSeveral(size, number * size));
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            throw new ServiceException(e);
        }
    }

    @Override
    public void remove(long id) throws ServiceException {
        try {
            dao.remove(id);
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            throw new ServiceException(e);
        }
    }

    @Override
    public T insert(T entity) throws ServiceException {
        try {
            return dao.insert(entity);
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            throw new ServiceException(e);
        }
    }

    @Override
    public T update(long id, T updateValue) throws ServiceException {
        try {
            return dao.update(id, updateValue);
        } catch (ConnectionException | DAOException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + ">");
            throw new ServiceException(e);
        } catch (NotFoundException e) {
            logger.warn("[Catch] <" + e.getClass().getSimpleName() + ">");
            throw new ServiceException(e);
        }
    }

    @Override
    public long count() throws ServiceException {
        try {
            return dao.count();
        } catch (ConnectionException | DAOException e) {
            logger.warn("[Catch] <" + e.getClass().getSimpleName() + ">");
            throw new ServiceException(e);
        }
    }

}
