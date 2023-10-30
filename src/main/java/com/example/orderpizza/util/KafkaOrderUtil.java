package com.example.orderpizza.util;


import com.example.orderpizza.exception.HttpError;

public class KafkaOrderUtil {
    public static final String ORDER_CREATED_TOPIC="order.created";

    public static final Integer ERR_2000_CODE = 2000;
    public static final String ERR_2000_MSG = "not retryable";
    public static final String ERR_2000_REASON = "producer failure";


    public static HttpError getNotRetryableHttpError() {
        return HttpError.builder()
                .code(ERR_2000_CODE)
                .message(ERR_2000_MSG)
                .reason(ERR_2000_REASON)
                .build();
    }

    private KafkaOrderUtil(){}
}
