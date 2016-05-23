package com.excilys.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Gives an access to the computer database.
 */
@Component
public class ConnectionManager {

    private Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    @Autowired
    @Qualifier("dataSource")
    private HikariDataSource ds;

    /**
     * Returns a fresh connection to the database.
     * @return a fresh connection to the database
     * @throws ConnectionException
     * @throws ConnectionException if no connection is reachable
     */
    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            throw new ConnectionException(e);
        }
    }

}
