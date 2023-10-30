package com.example.orderpizza.exception;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class HttpErrorDetail implements Serializable {
    private String target;
    private String message;
}
