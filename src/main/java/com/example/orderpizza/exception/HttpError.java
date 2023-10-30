package com.example.orderpizza.exception;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class HttpError implements Serializable {
    private Integer code;
    private String reason;
    private String message;
    private String body;
    private List<HttpErrorDetail> details;
}
