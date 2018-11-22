package com.rapp.email.exception;

@SuppressWarnings("serial")
public class ConfigurationException extends Exception {

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(Throwable cause) {
        super(cause);
    }

    public ConfigurationException(String message, Throwable ex) {
        super(message, ex);
    }

}
