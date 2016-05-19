package com.excilys.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.excilys.model.Company;

@Component
public class DAOCompanyMapper implements DAOMappable<Company> {

    private static final String ID = "id";
    private static final String NAME = "name";

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
