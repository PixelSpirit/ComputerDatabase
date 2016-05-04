package com.excilys.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

/**
 * Gives an access to the computer database.
 */
public enum ConnectionFactory {
    INSTANCE;

    private static final String PROPERTY_FILE = "db.properties";
    private static final Properties PROPS = new Properties();

    private static final HikariDataSource DS = new HikariDataSource();

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
            DS.setJdbcUrl(PROPS.getProperty("DB_URL"));
            DS.setUsername(PROPS.getProperty("DB_USERNAME"));
            DS.setPassword(PROPS.getProperty("DB_PASSWORD"));
            return DS.getConnection();
        } catch (SQLException | SecurityException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            throw new ConnectionException(e);
        }
    }

}
