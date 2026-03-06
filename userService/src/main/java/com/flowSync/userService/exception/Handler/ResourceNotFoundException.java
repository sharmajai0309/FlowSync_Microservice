
package com.flowSync.userService.exception.Handler;

import com.flowSync.userService.exception.AbstractException;

public class ResourceNotFoundException extends RuntimeException implements AbstractException{

    Integer statusCode;
    
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public String getErrorMessage() {
        return getMessage();
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
        
    }

    
}