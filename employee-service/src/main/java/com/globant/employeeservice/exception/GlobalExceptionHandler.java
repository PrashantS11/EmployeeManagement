package com.globant.employeeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleEmployeeNotFoundException(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), LocalDateTime.now(), "NOT_FOUND");
        return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), LocalDateTime.now(), "NOT_FOUND");
        return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
 }
