package com.example.equityfeedsprocessing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExternalTransactionIdNotFoundException extends RuntimeException {

    public ExternalTransactionIdNotFoundException(String externalTransactionId) {
        super("External Transaction Id: " + externalTransactionId + " Not Found. ");
    }
}
