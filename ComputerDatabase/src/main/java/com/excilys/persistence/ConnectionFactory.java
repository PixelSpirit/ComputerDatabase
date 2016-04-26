package com.excilys.persistence;

import java.io.FileInputStream;
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
public class ConnectionFactory {

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
            Properties props = new Properties();
            props.load(new FileInputStream("src/main/resources/db.properties"));
            Class.forName(props.getProperty("DB_DRIVER"));
            return DriverManager.getConnection(props.getProperty("DB_URL"), props.getProperty("DB_USERNAME"),
                    props.getProperty("DB_PASSWORD"));
        } catch (SQLException | IOException | SecurityException | ClassNotFoundException e) {
            logger.error("[Catch] <" + e.getClass().getSimpleName() + "> " + e.getMessage());
            throw new ConnectionException(e);
        }
    }
}