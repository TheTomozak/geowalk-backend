package com.example.geowalk.exceptions;


public class BadRequestImageException extends RuntimeException {

    public BadRequestImageException() {
    }

    public BadRequestImageException(String message) {
        super(message);
    }
}
