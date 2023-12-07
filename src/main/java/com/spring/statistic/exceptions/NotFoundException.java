package com.spring.statistic.exceptions;


import com.spring.statistic.exceptions.base.GlobalStatisticException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends GlobalStatisticException {
    public NotFoundException(HttpStatus status, String errorMessage, long timestamp) {
        super(status, errorMessage, timestamp);
    }
}
