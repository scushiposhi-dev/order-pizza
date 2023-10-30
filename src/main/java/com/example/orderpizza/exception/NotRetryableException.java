package com.example.orderpizza.exception;

import org.springframework.http.HttpStatus;

public class NotRetryableException extends AbstractOrderHttpException{
    public NotRetryableException(HttpStatus httpStatus,HttpError httpError) {
        super(httpStatus,httpError);
    }
    public NotRetryableException(HttpError httpError){
        super(HttpStatus.SERVICE_UNAVAILABLE,httpError);
    }
}
