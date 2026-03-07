package com.flowsync.authService.Exception;

public class JwtValidationException extends RuntimeException implements AbstractEntity {

    private final Integer statusCode;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public JwtValidationException(String message, Integer statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
    }

    @Override
    public String getStatusMessage() {
        return getMessage();
    }
}
