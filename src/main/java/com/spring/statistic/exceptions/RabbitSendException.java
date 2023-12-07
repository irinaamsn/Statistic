package com.spring.statistic.exceptions;

import com.spring.statistic.exceptions.base.GlobalStatisticException;
import org.springframework.http.HttpStatus;

public class RabbitSendException extends GlobalStatisticException {
    public RabbitSendException(HttpStatus status, String errorMessage, long timestamp) {
        super(status, errorMessage, timestamp);
    }
}
