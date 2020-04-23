package com.example.equityfeedsprocessing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientIdNotFoundException extends RuntimeException {

    public ClientIdNotFoundException(String clientId) {
        super("Client Id: " + clientId + " Not Found. ");
    }
}
