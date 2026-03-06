package com.flowSync.userService.exception.Handler;

import com.flowSync.userService.exception.AbstractException;

public class ValidationException extends RuntimeException implements AbstractException{

    Integer statusCode;

    public ValidationException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }


    public ValidationException(String message) {
        super(message);
    }

    @Override
    public Integer getStatusCode() {
       return statusCode;
    }

    @Override
    public String getErrorMessage() {
       return getMessage();
    }
    
}
