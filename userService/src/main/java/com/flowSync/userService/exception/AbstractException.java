package com.flowSync.userService.exception;

public interface AbstractException {
    Integer getStatusCode();
    String getErrorMessage();
}