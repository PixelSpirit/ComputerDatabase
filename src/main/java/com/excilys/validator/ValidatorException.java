package com.excilys.validator;

@SuppressWarnings("serial")
public class ValidatorException extends RuntimeException {

    /**
     * Constructs a ValidatorException.
     */
    public ValidatorException() {
        super();
    }

    /**
     * Constructs a ValidatorException.
     * @param message The exception message
     * @param cause The cause of the exception
     * @param enableSuppression The enableSuppression
     * @param writableStackTrace The writable stack trace
     */
    public ValidatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Constructs a ValidatorException.
     * @param message The exception message
     * @param cause The cause of the exception
     */
    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a ValidatorException.
     * @param message @param message The exception message
     */
    public ValidatorException(String message) {
        super(message);
    }

    /**
     * Constructs a ValidatorException.
     * @param cause cause The cause of the exception
     */
    public ValidatorException(Throwable cause) {
        super(cause);
    }

}
