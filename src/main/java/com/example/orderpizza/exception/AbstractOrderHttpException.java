package com.example.orderpizza.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class AbstractOrderHttpException extends RuntimeException{
    protected HttpStatus httpStatus;
    protected HttpError httpError;
}
