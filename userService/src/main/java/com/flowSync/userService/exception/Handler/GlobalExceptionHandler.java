package com.flowSync.userService.exception.Handler;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flowSync.userService.exception.AbstractException;
import com.flowSync.userService.utils.CommanUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions for method arguments.
     * 
     * @param ex the MethodArgumentNotValidException containing binding result with field errors
     * @return ResponseEntity with HTTP 400 status and a map of field names to their validation error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex){
        Map<String,String> errors  = new HashMap<>();
         ex.getBindingResult().getFieldErrors().forEach((fieldError) -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }


    /**
     * Handles ResourceNotFoundException thrown during request processing.
     * 
     * @param exception the ResourceNotFoundException containing error details
     * @return a ResponseEntity with HTTP 404 NOT_FOUND status and the exception message as response body
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException exception){
        return genericExceptionHandler(exception,
                () -> ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(exception.getMessage()));
    }



    /**
     * Handles ValidationException thrown during request validation.
     * 
     * @param exception the ValidationException containing the validation error details
     * @return ResponseEntity with HTTP 404 status and the exception message as the response body
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String>handleValidateException(ValidationException exception){
        return genericExceptionHandler(exception, () -> ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(exception.getMessage()));
    }


    /**
     * Handles generic exceptions by returning an appropriate HTTP response.
     * 
     * <p>If the exception contains a valid status code, returns a ResponseEntity with that status
     * and the exception's error message. Otherwise, delegates to the provided fallback handler.</p>
     * 
     * @param exception the AbstractException to handle, containing status code and error message
     * @param runner a Supplier providing a fallback ResponseEntity if the exception has no status code
     * @return a ResponseEntity with the appropriate HTTP status and error message
     */
    private ResponseEntity<String> genericExceptionHandler(final AbstractException exception, final Supplier<ResponseEntity<String>> runner) {
       if(CommanUtils.isNotEmpty(exception.getStatusCode())){
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getErrorMessage());
       }
       return runner.get();
    }


    
/*
Exception Handling Flow:


 ValidationException / ResourceNotFoundException
           |
           v
  handleXException()
           |
           v
 genericExceptionHandler()
           |
     ----------------
     |              |
statusCode? YES   statusCode? NO
     |              |
     v              v
Custom status     Supplier.run()
     |              |
     v              v
HTTP Response   HTTP Response


 */



    
}
