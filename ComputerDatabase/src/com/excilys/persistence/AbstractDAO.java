package com.excilys.persistence;

import java.util.LinkedList;

/**
 * Permits to access to the database to control the mapping
 * of a table as an entity.
 * @param <T> The type of the entity that represents a table
 */
public abstract class AbstractDAO<T> {
	
	/**
	 * Finds the entity that match the given id.
	 * @param id The entity's id
	 * @return The required entity
	 * @throws ConnectionException if the connection to the database was refused
	 * @throws DAOException if no query can be done on database
	 */
	public abstract T find(long id) throws ConnectionException, DAOException;
	
	/**
	 * Finds n elements from the offset in the databases.
	 * @param n The maximum number of elements that must be found
	 * @param offset The first 
	 * @throws ConnectionException if the connection to the database was refused
	 * @throws DAOException if no query can be done on database
	 * @return the list of the desired entities
	 */
	public abstract LinkedList<T> findSeveral(int n, int offset) throws ConnectionException, DAOException;
	
	/**
	 * Remove the entity that match the given id from
	 * the database and returns it.
	 * @param id The entity's id
	 * @throws ConnectionException if the connection to the database was refused
	 * @throws DAOException if no query can be done on database
	 */
	public abstract void remove(long id) throws ConnectionException, DAOException;
	
	/**
	 * Insert the given entity in the database.
	 * @param entity The entity to add in the database
	 * @return The entity that has been inserted
	 * @throws ConnectionException if the connection to the database was refused
	 * @throws DAOException if no query can be done on database
	 */
	public abstract T insert(T entity) throws ConnectionException, DAOException;
	
	/**
	 * Find the entity that match the given id and update it
	 * with the update value.
	 * @param id The entity's id
	 * @param updateValue The new value that will have the desired
	 * entity.
	 * @return The entity that has been updated
	 * @throws ConnectionException if the connection to the database was refused
	 * @throws DAOException if no query can be done on database
	 */
	public abstract T update(long id, T updateValue) throws ConnectionException, DAOException;

	
}

