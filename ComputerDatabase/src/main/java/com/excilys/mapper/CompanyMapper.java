package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.model.Company;

public class CompanyMapper implements DAOMappable<Company> {

    private static final String ID = "id";
    private static final String NAME = "name";

    /* Singleton */

    private static CompanyMapper instance = null;

    /**
     * Constructs a CompanyMapper.
     */
    private CompanyMapper() {
        super();
    }

    /**
     * @return The unique instance of a CompanyMapper.
     */
    public static CompanyMapper getInstance() {
        if (instance == null) {
            synchronized (CompanyMapper.class) {
                if (instance == null) {
                    instance = new CompanyMapper();
                }
            }
        }
        return instance;
    }

    /* Mapper */

    @Override
    public void map(Company entity, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, entity.getName());
    }

    @Override
    public Company unmap(ResultSet databaseRow) throws SQLException {
        Company entity = new Company(databaseRow.getLong(ID));
        entity.setName(databaseRow.getString(NAME));
        return entity;
    }

}
