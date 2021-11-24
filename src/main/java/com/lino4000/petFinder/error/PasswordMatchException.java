package com.lino4000.petFinder.error;

@SuppressWarnings("serial")
public class PasswordMatchException extends RuntimeException {

    public PasswordMatchException() {
        super();
    }

    public PasswordMatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PasswordMatchException(final String message) {
        super(message);
    }

    public PasswordMatchException(final Throwable cause) {
        super(cause);
    }
}
