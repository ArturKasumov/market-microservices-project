package com.arturk.storage.service;

import com.arturk.common.exception.BusinessMarketAppException;
import com.arturk.common.exception.CommonExceptionHandler;
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
public class StorageExceptionHandler extends CommonExceptionHandler {

}
