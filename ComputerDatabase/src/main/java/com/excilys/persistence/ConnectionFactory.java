package com.excilys.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gives an access to the computer database.
 */
enum ConnectionFactory {
    INSTANCE;

    private static final String PROPERTY_FILE = "db.properties";

    private static final Properties PROPS = new Properties();

    private Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

    static {
        try {
            PROPS.load(ConnectionFactory.class.getClassLoader().getResourceAsStream(PROPERTY_FILE));
            Class.forName(PROPS.getProperty("DB_DRIVER"));
        } catch (ClassNotFoundException e) {
            throw new ConnectionException();
        } catch (IOException e) {
            throw new ConnectionException();
        }
    }

    /**
     * Returns a fresh connection to the database.
     * @return a fresh connection to the database
     * @throws ConnectionException
     * @throws ConnectionException if no connection is reachable
     */
    public Connection get() throws ConnectionException {
        try {

            return DriverManager.getConnection(PROPS.getProperty("DB_URL"), PROPS.getProperty("DB_USERNAME"),
                    PROPS.getProperty("DB_PASSWORD"));
        } catch (SQLException | SecurityException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            throw new ConnectionException(e);
        }
    }

}
