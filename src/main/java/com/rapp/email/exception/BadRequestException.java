package com.rapp.email.exception;

public class BadRequestException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(final ErrorMap error) {
        super(error.getDescription());
    }

    public enum ErrorMap {
        INVALID_EMAIL("Invalid email address");

        ErrorMap(String description) {
            this.description = description;
        }

        private String description;


        public String getDescription() {
            return description;
        }
    }
}
