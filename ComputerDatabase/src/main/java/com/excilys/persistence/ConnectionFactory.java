package com.excilys.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gives an access to the computer database.
 */
public class ConnectionFactory {

    private static final String USER = "admincdb";
    private static final String PASSWORD = "qwerty1234";
    private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db";

    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    /**
     * Returns a fresh connection to the database.
     *
     * @return a fresh connection to the database
     * @throws ConnectionException
     * @throws ConnectionException if no connection is reachable
     */
    public static Connection get() throws ConnectionException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            logger.error("[Catch] <SQLException> Can't connect to database !");
            logger.error(e.getSQLState());
            logger.warn("[Throw] <ConnectionException>");
            throw new ConnectionException(e);
        }
    }
}
