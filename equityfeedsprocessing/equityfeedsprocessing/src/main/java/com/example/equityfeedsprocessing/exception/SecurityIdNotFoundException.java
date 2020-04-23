package com.example.equityfeedsprocessing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SecurityIdNotFoundException extends RuntimeException {

    public SecurityIdNotFoundException(String securityId) {
        super("Security Id: " + securityId + " Not Found. ");
    }
}
