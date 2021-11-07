package com.example.geowalk.exceptions;

public class NotFoundBlogPostException extends RuntimeException {
    public NotFoundBlogPostException(String message) {
        super(message);
    }
}
