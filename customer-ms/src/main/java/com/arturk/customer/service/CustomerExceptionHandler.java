package com.arturk.customer.service;

import com.arturk.common.exception.BusinessMarketAppException;
import com.arturk.common.exception.CommonExceptionHandler;
import com.arturk.common.exception.RestException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class CustomerExceptionHandler extends CommonExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<RestException>> handleConstraintViolationException(ConstraintViolationException exception) {
        List<RestException> restExceptions = new ArrayList<>();
        exception.getConstraintViolations().forEach(i -> {
            String propertyName = i.getPropertyPath().toString().substring(
                    i.getPropertyPath().toString().lastIndexOf('.') + 1
            );
            RestException restException = new RestException(propertyName, i.getMessage());
            restExceptions.add(restException);
        });
        return new ResponseEntity<>(restExceptions, HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()));
    }
}
