package com.wipro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// Global exception handler that handles exceptions globally
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation exceptions (like invalid title input)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        // Get all validation errors and return them in a map
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();  
            String errorMessage = error.getDefaultMessage();   
            errors.put(fieldName, errorMessage);                
        });

      
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

   
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidDataException(InvalidDataException ex) {
        // Create an ExceptionResponse object for the custom error message
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ex.getMessage(), 
                System.currentTimeMillis(), 
                HttpStatus.BAD_REQUEST.value()
        );
        
    
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

 
    @ExceptionHandler(BlogNotFoundException.class)
    public ResponseEntity<String> handleBlogNotFoundException(BlogNotFoundException ex) {
    
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
     
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    
}
