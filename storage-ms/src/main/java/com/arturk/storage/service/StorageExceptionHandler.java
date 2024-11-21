package com.arturk.storage.service;

import com.arturk.common.exception.MarketAppException;
import com.arturk.common.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class StorageExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MarketAppException.class)
    public ResponseEntity<RestException> handleCustomerNotFound(MarketAppException exception) {
        log.error("Error occurred", exception);
        RestException restException = new RestException(exception.getCode(), exception.getDescription(), exception.getDetails());
        return new ResponseEntity<>(restException, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestException> handleException(Exception exception) {
        log.error("Error occurred", exception);
        RestException restException = new RestException(exception.getMessage());
        return new ResponseEntity<>(restException, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
