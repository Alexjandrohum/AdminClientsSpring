package com.clients.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity exceptionDetail(ApiException exception, WebRequest request) {
        MessageGeneric messageGeneric = new MessageGeneric(exception.getCode().value(), exception.getMsg(), exception.getDetails());
        return new ResponseEntity<>(messageGeneric, exception.getCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionGeneric(Exception exception, WebRequest request) {
        MessageGeneric messageGeneric = new MessageGeneric(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), new ArrayList<String>(Arrays.asList(exception.getMessage())));
        return new ResponseEntity(messageGeneric, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
