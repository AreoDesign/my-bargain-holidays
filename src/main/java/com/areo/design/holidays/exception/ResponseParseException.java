package com.areo.design.holidays.exception;

public class ResponseParseException extends RuntimeException {

    private static final long serialVersionUID = -6682916406187811365L;

    public ResponseParseException() {
    }

    public ResponseParseException(String message) {
        super(message);
    }

}
