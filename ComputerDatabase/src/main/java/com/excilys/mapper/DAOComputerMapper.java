package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class DAOComputerMapper implements DAOMappable<Computer> {

    private static final String ID = "cptr.id";
    private static final String NAME = "cptr.name";
    private static final String INTRODUCED = "cptr.introduced";
    private static final String DISCONTINUED = "cptr.discontinued";
    private static final String COMPANY_ID = "cpn.id";
    private static final String COMPANY_NAME = "cpn.name";

    /* Singleton */

    private static DAOComputerMapper instance = null;

    /**
     * Constructs a ComputerMapper.
     */
    private DAOComputerMapper() {
        super();
    }

    /**
     * @return The unique instance of a ComputerMapper.
     */
    public static DAOComputerMapper getInstance() {
        if (instance == null) {
            synchronized (DAOComputerMapper.class) {
                if (instance == null) {
                    instance = new DAOComputerMapper();
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
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.of(entity.getIntroduced(), LocalTime.MIN)));
        } else {
            stmt.setNull(2, java.sql.Types.TIMESTAMP);
        }
        if (entity.getDiscontinued() != null) {
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.of(entity.getDiscontinued(), LocalTime.MIN)));
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
        Computer cpt = new Computer.Builder(databaseRow.getString(NAME)).id(databaseRow.getLong(ID))
                .introduced(
                        (introduced != null) ? LocalDateTime.parse(introduced, Computer.formatter).toLocalDate() : null)
                .discontinued((discontinued != null)
                        ? LocalDateTime.parse(discontinued, Computer.formatter).toLocalDate() : null)
                .company(cpn).build();
        return cpt;
    }

}
