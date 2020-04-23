package com.example.equityfeedsprocessing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExternalTransactionIdNotFoundException.class)
    @ResponseBody
    public final ResponseEntity<ExceptionResponse> handleExternalTransactionIdNotFound(ExternalTransactionIdNotFoundException externalTransactionIdNotFoundException, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), externalTransactionIdNotFoundException.getMessage(), request.getDescription(false), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ExceptionResponse> (exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ClientIdNotFoundException.class)
    @ResponseBody
    public final ResponseEntity<ExceptionResponse> handleClientIdNotFound(ClientIdNotFoundException clientIdNotFoundException, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), clientIdNotFoundException.getMessage(), request.getDescription(false), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ExceptionResponse> (exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(SecurityIdNotFoundException.class)
    @ResponseBody
    public final ResponseEntity<ExceptionResponse> handleSecurityIdNotFound(SecurityIdNotFoundException securityIdNotFoundException, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), securityIdNotFoundException.getMessage(), request.getDescription(false), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ExceptionResponse> (exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ExceptionResponse> (exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
