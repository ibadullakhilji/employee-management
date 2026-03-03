package com.example.employeemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice       // marks this class as a global error handler for all controllers
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)     // This method will handle EmployeeNotFoundException whenever it is thrown in any controller, catches this specific exception anywhere in the app
    public ResponseEntity<ErrorResponse> handEmployeeNotFound(EmployeeNotFoundException ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value() , ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)      // catches any other unexpected exception as a fallback
    public ResponseEntity<ErrorResponse> handleGenericException( Exception ex){
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong: " + ex.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
