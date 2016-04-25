package com.excilys.persistence;

@SuppressWarnings("serial")
public class DAOException extends Exception {

    /**
     * Constructs a DAOException.
     */
    public DAOException() {
        super();
    }

    /**
     * Constructs a DAOException.
     * @param message The exception message
     * @param cause The cause of the exception
     * @param enableSuppression The enableSuppression
     * @param writableStackTrace The writable stack trace
     */
    public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructs a DAOException.
     * @param message The exception message
     * @param cause The cause of the exception
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a DAOException.
     * @param message @param message The exception message
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a DAOException.
     * @param cause cause The cause of the exception
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

}
