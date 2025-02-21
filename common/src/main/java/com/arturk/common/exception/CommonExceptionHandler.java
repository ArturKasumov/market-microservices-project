package com.arturk.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestException> handleException(Exception exception) {
        log.error("Error occurred", exception);
        RestException restException = new RestException(exception.getMessage());
        return new ResponseEntity<>(restException, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TechnicalMarketAppException.class)
    public ResponseEntity<RestException> handleException(TechnicalMarketAppException exception) {
        log.error("Technical error occurred", exception);
        RestException restException = new RestException(exception.getCode(), exception.getDescription(), exception.getDetails());
        return new ResponseEntity<>(restException, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessMarketAppException.class)
    public ResponseEntity<RestException> handleException(BusinessMarketAppException exception) {
        log.error("Business error occurred", exception);
        RestException restException = new RestException(exception.getCode(), exception.getDescription(), exception.getDetails());
        return new ResponseEntity<>(restException, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
}
