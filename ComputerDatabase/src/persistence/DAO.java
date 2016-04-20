package persistence;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Permits to access to the database to control the mapping
 * of a table as an entity.
 * @param <T> The type of the entity that represents a table
 */
public abstract class DAO<T> implements AutoCloseable {
	
	protected Connection connect = null;
	
	public DAO() throws SQLException {
		this.connect = Database.getFreshConnection();
	}
	
	@Override
	public void close() throws Exception {
		this.connect.close();
	}
	

	/* DAO Specification */
	
	/**
	 * Finds the entity that match the given id.
	 * @param id The entity's id
	 * @return The required entity
	 */
	public abstract T find(long id) throws SQLException;
	
	/**
	 * Remove the entity that match the given id from
	 * the database and returns it.
	 * @param id The entity's id
	 */
	public abstract void remove(long id) throws SQLException;
	
	/**
	 * Insert the given entity in the database.
	 * @param entity The entity to add in the database
	 * @return The entity that has been inserted

	 */
	public abstract T insert(T entity) throws SQLException;
	
	/**
	 * Find the entity that match the given id and update it
	 * with the update value.
	 * @param id The entity's id
	 * @param updateValue The new value that will have the desired
	 * entity.
	 * @return The entity that has been updated
	 */
	public abstract T update(long id, T updateValue) throws SQLException;

	
}

