package com.excilys.service;

@SuppressWarnings("serial")
public class ServiceException extends Exception {

    /**
     * Constructs a ServiceException.
     */
    public ServiceException() {
        super();
    }

    /**
     * Constructs a ServiceException.
     * @param message The exception message
     * @param cause The cause of the exception
     * @param enableSuppression The enableSuppression
     * @param writableStackTrace The writable stack trace
     */
    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructs a ServiceException.
     * @param message The exception message
     * @param cause The cause of the exception
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a ServiceException.
     * @param message The exception message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a ServiceException.
     * @param cause The cause of the exception
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

}
