package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.RowSet;

import model.Computer;

public class ComputerMapper implements Mapper<Computer> {
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String INTRODUCED = "introduced";
	private static final String DISCONTINUED = "discontinued";
	private static final String COMPANY_ID = "company_id";
	
	private static ComputerMapper _instance = null;
	
	private ComputerMapper() {
		super();
	}
	
	synchronized public static ComputerMapper getInstance() {
		if(_instance == null) {
			_instance = new ComputerMapper();
		}
		return _instance;
	}

	@Override
	public String map(Computer entity) throws SQLException {
		return null;
//		databaseRow.setString(NAME, entity.getName());
//		databaseRow.setTimestamp(INTRODUCED, entity.getIntroduced());
//		databaseRow.setTimestamp(DISCONTINUED, entity.getDiscontinued());
//		databaseRow.setLong(COMPANY_ID, entity.getCompanyId());
	}

	@Override
	public Computer unmap(ResultSet databaseRow) throws SQLException {
		Computer entity = new Computer(databaseRow.getLong(ID));
		entity.setName(databaseRow.getString(NAME));
		entity.setIntroduced(databaseRow.getTimestamp(INTRODUCED));
		entity.setDiscontinued(databaseRow.getTimestamp(DISCONTINUED));
		entity.setCompanyId(databaseRow.getLong(COMPANY_ID));
		return entity;
	}

}
