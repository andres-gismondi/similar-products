package com.zara.similarproducts.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({ProductServiceException.class})
    public ResponseEntity<String> serviceException(ProductServiceException exception) {
        String errorResponse = exception.getMessage();
        return new ResponseEntity<>(errorResponse, HttpStatus.FAILED_DEPENDENCY);
    }

}
