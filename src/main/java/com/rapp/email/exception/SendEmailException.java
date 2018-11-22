package com.rapp.email.exception;

public class SendEmailException extends Exception {

    private static final long serialVersionUID = 1L;

    public SendEmailException() {
        super();
    }

    public SendEmailException(final String message) {
        super(message);
    }
}
