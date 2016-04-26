package com.excilys.persistence;

@SuppressWarnings("serial")
public class NotFoundException extends Exception {

    /**
     * Constructs a DAOException.
     */
    public NotFoundException() {
        super();
    }

    /**
     * Constructs a NotFoundException.
     * @param message The exception message
     * @param cause The cause of the exception
     * @param enableSuppression The enableSuppression
     * @param writableStackTrace The writable stack trace
     */
    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructs a NotFoundException.
     * @param message The exception message
     * @param cause The cause of the exception
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a NotFoundException.
     * @param message @param message The exception message
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a NotFoundException.
     * @param cause cause The cause of the exception
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }

}
