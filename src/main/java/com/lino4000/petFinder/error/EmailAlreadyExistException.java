package com.lino4000.petFinder.error;

@SuppressWarnings("serial")
public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException() {
        super();
    }

    public EmailAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistException(final String message) {
        super(message);
    }

    public EmailAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}