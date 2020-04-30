package com.example.equityfeedsprocessing.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;

public class ExceptionResponse {

    private LocalDate timestamp;
    private String message;
    private String details;
    private String httpCodeMessage;
    private HttpStatus status;
    private int statusValue;

    public ExceptionResponse(LocalDate timestamp, String message, String details, String httpCodeMessage, HttpStatus status, int statusValue) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
        this.httpCodeMessage = httpCodeMessage;
        this.status = status;
        this.statusValue = statusValue;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getHttpCodeMessage() {
        return httpCodeMessage;
    }

    public void setHttpCodeMessage(String httpCodeMessage) {
        this.httpCodeMessage = httpCodeMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public int getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
    }
}
