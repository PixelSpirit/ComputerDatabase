package com.excilys.persistence;

@SuppressWarnings("serial")
public class ConnectionException extends Exception {

    /**
     * Constructs a ConnectionException.
     */
    public ConnectionException() {
        super();
    }

    /**
     * Constructs a ConnectionException.
     * @param message The exception message
     * @param cause The cause of the exception
     * @param enableSuppression The enableSuppression
     * @param writableStackTrace The writable stack trace
     */
    public ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructs a ConnectionException.
     * @param message The exception message
     * @param cause The cause of the exception
     */
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a ConnectionException.
     * @param message The exception message
     */
    public ConnectionException(String message) {
        super(message);
    }

    /**
     * Constructs a ConnectionException.
     * @param cause The cause of the exception
     */
    public ConnectionException(Throwable cause) {
        super(cause);
    }

}
