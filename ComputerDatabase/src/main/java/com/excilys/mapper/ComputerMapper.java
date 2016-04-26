package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerMapper implements DAOMappable<Computer> {

    private static final String ID = "cptr.id";
    private static final String NAME = "cptr.name";
    private static final String INTRODUCED = "cptr.introduced";
    private static final String DISCONTINUED = "cptr.discontinued";
    private static final String COMPANY_ID = "cpn.id";
    private static final String COMPANY_NAME = "cpn.name";

    /* Singleton */

    private static ComputerMapper instance = null;

    /**
     * Constructs a ComputerMapper.
     */
    private ComputerMapper() {
        super();
    }

    /**
     * @return The unique instance of a ComputerMapper.
     */
    public static ComputerMapper getInstance() {
        if (instance == null) {
            synchronized (ComputerMapper.class) {
                if (instance == null) {
                    instance = new ComputerMapper();
                }
            }
        }
        return instance;
    }

    /* Mapper */

    @Override
    public void map(Computer entity, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, entity.getName());
        if (entity.getIntroduced() != null) {
            stmt.setTimestamp(2, Timestamp.valueOf(entity.getIntroduced()));
        } else {
            stmt.setNull(2, java.sql.Types.TIMESTAMP);
        }
        if (entity.getDiscontinued() != null) {
            stmt.setTimestamp(3, Timestamp.valueOf(entity.getDiscontinued()));
        } else {
            stmt.setNull(3, java.sql.Types.TIMESTAMP);
        }
        if (entity.getCompany() != null) {
            stmt.setLong(4, entity.getCompany().getId());
        } else {
            stmt.setNull(4, java.sql.Types.BIGINT);
        }
    }

    @Override
    public Computer unmap(ResultSet databaseRow) throws SQLException {
        String introduced = databaseRow.getString(INTRODUCED);
        String discontinued = databaseRow.getString(DISCONTINUED);
        Company cpn = null;
        if (databaseRow.getLong(COMPANY_ID) != 0) {
            cpn = new Company(databaseRow.getLong(COMPANY_ID), databaseRow.getString(COMPANY_NAME));
        }
        Computer cpt = new Computer.Builder().id(databaseRow.getLong(ID)).name(databaseRow.getString(NAME))
                .introduced((introduced != null) ? LocalDateTime.parse(introduced, Computer.formatter) : null)
                .discontinued((discontinued != null) ? LocalDateTime.parse(discontinued, Computer.formatter) : null)
                .company(cpn).build();
        return cpt;
    }

}
