package mapper;

import java.sql.SQLException;

import javax.sql.RowSet;

import model.Computer;

public class ComputerMapper implements Mapper<Computer> {
	
	private static final String id = "id";
	private static final String name = "name";
	private static final String introduced = "introduced";
	private static final String discontinued = "discontinued";
	private static final String companyId = "company_id";
	
	
	public ComputerMapper() {
		super();
	}

	@Override
	public void map(Computer entity, RowSet databaseRow) throws SQLException {
		databaseRow.setString(name, entity.getName());
		databaseRow.setTimestamp(introduced, entity.getIntroduced());
		databaseRow.setTimestamp(discontinued, entity.getDiscontinued());
		databaseRow.setLong(companyId, entity.getCompanyId());
	}

	@Override
	public Computer unmap(RowSet databaseRow) throws SQLException {
		Computer entity = new Computer(databaseRow.getLong(id));
		entity.setName(databaseRow.getString(name));
		entity.setIntroduced(databaseRow.getTimestamp(introduced));
		entity.setDiscontinued(databaseRow.getTimestamp(discontinued));
		entity.setCompanyId(databaseRow.getLong(companyId));
		return entity;
	}

}
