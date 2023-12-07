package com.spring.statistic.exceptions;

import com.spring.statistic.exceptions.base.GlobalStatisticException;
import org.springframework.http.HttpStatus;

public class MapperCovertException extends GlobalStatisticException {
    public MapperCovertException(HttpStatus status, String errorMessage, long timestamp) {
        super(status, errorMessage, timestamp);
    }
}
