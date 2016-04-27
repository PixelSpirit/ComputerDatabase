package com.excilys.service;

import com.excilys.model.Page;
import com.excilys.persistence.ConnectionException;
import com.excilys.persistence.NotFoundException;

/**
 * A generic service.
 *
 * @param <T> The type of the entity that handles the service
 */
public abstract class AbstractService<T> {

    /**
     * Finds the entity that match the given id.
     *
     * @param id The entity's id
     * @return The required entity
     * @throws ServiceException if the service isn't available
     * @throws NotFoundException if no entity was found
     */
    public abstract T find(long id) throws ServiceException, NotFoundException;

    /**
     * Finds n elements from the offset in the databases.
     *
     * @param number The number of the page
     * @param size The size of the page
     * @return the page containing the list of the desired entities
     * @throws ConnectionException if the connection to the database was refused
     * @throws ServiceException if the service isn't available
     */
    public abstract Page<T> findPage(int number, int size) throws ServiceException;

    /**
     * Remove the entity that match the given id from the database and returns
     * it.
     *
     * @param id The entity's id
     * @throws ServiceException if the service isn't available
     */
    public abstract void remove(long id) throws ServiceException;

    /**
     * Insert the given entity in the database.
     *
     * @param entity The entity to add in the database
     * @return The entity that has been inserted
     * @throws ServiceException if the service isn't available
     */
    public abstract T insert(T entity) throws ServiceException;

    /**
     * Find the entity that match the given id and update it with the update
     * value.
     *
     * @param id The entity's id
     * @param updateValue The new value that will have the desired entity.
     * @return The entity that has been updated
     * @throws ServiceException if the service isn't available
     */
    public abstract T update(long id, T updateValue) throws ServiceException;

    /**
     * @return The number of entity that handle the service
     * @throws ServiceException if the service isn't available
     */
    public abstract long count() throws ServiceException;

}
