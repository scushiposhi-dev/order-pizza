package com.example.orderpizza.exception;

import org.openapitools.model.GenericError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotRetryableException.class)
    public ResponseEntity<GenericError> handleUpdateOrderExc(NotRetryableException e) {
        GenericError genericError = fillInGenericError(e.httpError);
        return new ResponseEntity<>(genericError,e.httpStatus);
    }
    private GenericError fillInGenericError(HttpError httpError){
        GenericError genericError = new GenericError();
        genericError.setCode(httpError.getCode());
        genericError.setMessage(httpError.getMessage());
        genericError.setReason(httpError.getReason());
        return genericError;
    }
}
