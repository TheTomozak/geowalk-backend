package com.example.geowalk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler({BadRequestException.class, ForbiddenException.class, NotAcceptableException.class, NotFoundException.class, UnauthorizedException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException e) {
        HttpStatus status = null;
        if (e instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else if(e instanceof ForbiddenException) {
            status = HttpStatus.FORBIDDEN;
        } else if(e instanceof NotAcceptableException) {
            status = HttpStatus.NOT_ACCEPTABLE;
        } else if(e instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if(e instanceof UnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
        }

        RestApiException exceptionBody = new RestApiException(e.getMessage(), status);
        return new ResponseEntity<>(exceptionBody, status);
    }
}
