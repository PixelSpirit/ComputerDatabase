package mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

/**
 * Permits to map a database result into an entity and vice versa.
  * @param <T> The type of the entity to map into a table row
 */
public interface Mapper<T> {
	
	/**
	 * Fill the given PreparedStatement with the mapped entity.
	 * @param entity The entity to convert into a database row
	 * @param stmt The PreparedStatement to fill
	 */
	public void map(T entity, PreparedStatement stmt) throws SQLException;
	
	/**
	 * Converts a database row into an entity.
	 * @param databaseRow The database row to convert into an entity
	 * @return The resulted entity
	 */
	public T unmap(ResultSet databaseRow) throws SQLException;
	
	
}
