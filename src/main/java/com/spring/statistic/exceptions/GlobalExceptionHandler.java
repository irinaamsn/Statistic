package com.spring.statistic.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.statistic.dto.ErrorDtoResponse;
import com.spring.statistic.exceptions.base.GlobalStatisticException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalStatisticException.class)
    public ResponseEntity<ErrorDtoResponse> handleGlobalWeatherException(GlobalStatisticException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorDtoResponse(e.getStatus().value(), e.getErrorMessage()));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorDtoResponse> handleSqlException(DataAccessException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDtoResponse(HttpStatus.BAD_REQUEST.value(),
                        "Invalid request"));
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorDtoResponse> handleJsonProcessingException(JsonProcessingException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDtoResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Failed to process JSON"));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDtoResponse> handleUnknownException(Throwable e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDtoResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Unknown error"));
    }
}
