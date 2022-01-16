package com.example.geowalk.exceptions;

public class NotAcceptableException extends RuntimeException {

    public NotAcceptableException() {
    }

    public NotAcceptableException(String message) {
        super(message);
    }
}
