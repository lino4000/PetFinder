package com.lino4000.petFinder.error;

@SuppressWarnings("serial")
public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException() {
        super();
    }

    public DeviceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DeviceNotFoundException(final String message) {
        super(message);
    }

    public DeviceNotFoundException(final Throwable cause) {
        super(cause);
    }
}