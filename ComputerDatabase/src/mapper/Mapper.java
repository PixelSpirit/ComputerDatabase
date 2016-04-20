package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

/**
 * Permits to map a database result into an entity and vice versa.
  * @param <T> The type of the entity to map into a table row
 */
public interface Mapper<T> {
	
	/**
	 * Converts an entity into a string that will represent the value
	 * field of an update or insert database query. 
	 * @param entity The entity to convert into a database row
	 * @return The value SQL query's field string
	 */
	public String map(T entity) throws SQLException;
	
	/**
	 * Converts a database row into an entity.
	 * @param databaseRow The database row to convert into an entity
	 * @return The resulted entity
	 */
	public T unmap(ResultSet databaseRow) throws SQLException;
	
	
}
