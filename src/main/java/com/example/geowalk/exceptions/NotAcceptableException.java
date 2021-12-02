package com.example.geowalk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptableException extends RuntimeException {

    public NotAcceptableException() {
    }

    public NotAcceptableException(String message) {
        super(message);
    }
}