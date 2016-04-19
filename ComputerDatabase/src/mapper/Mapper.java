package mapper;

import java.sql.SQLException;

import javax.sql.RowSet;

/**
 * Permits to map a database result into an entity and vice versa.
  * @param <T> The type of the entity to map into a table row
 */
public interface Mapper<T> {
	
	/**
	 * Writes the given entity's data into the database row
	 * @param entity The entity to convert into a database row
	 * @param databaseRow The database row to update
	 */
	public void map(T entity, RowSet databaseRow) throws SQLException;
	
	/**
	 * Convert a database row into an entity
	 * @param databaseRow The database row to convert into an entity
	 * @return The resulted entity
	 */
	public T unmap(RowSet databaseRow) throws SQLException;
	
	
}
