package com.example.geowalk.exceptions;

public class BadRequestBlogPostException extends RuntimeException {
    public BadRequestBlogPostException(String message) {
        super(message);
    }
}
