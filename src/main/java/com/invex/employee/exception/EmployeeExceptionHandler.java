package com.invex.employee.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class EmployeeExceptionHandler {

    /**
     * Handles the exception when a record is not found in the database.
     * 
     * @param exception Exception of type EmployeeNotFoundException
     * @return Response with the exception
     */
    @ExceptionHandler(value = { EmployeeNotFoundException.class })
    public ResponseEntity<Object> handleEmployeeNotFoundException(EmployeeNotFoundException exception) {
        log.info("Record not found");
        return new ResponseEntity<Object>(new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles errors when attempting to deserialize the body of a request
     * (e.g., improperly formatted dates).
     * 
     * @param ex Exception thrown by Jackson while parsing the JSON
     * @return Response with a readable error message
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Error deserializing JSON: {}", ex.getMessage());
        String message = "The request body format is invalid. Please check the data. \n" + ex.getMessage();
        return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation errors in request fields (e.g., incorrect date format).
     * 
     * @param ex Exception of type MethodArgumentNotValidException
     * @return Response with error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getMessage());

        BindingResult result = ex.getBindingResult();
        StringBuilder errorMessages = new StringBuilder("Validation errors:");

        // Iterate through validation errors and build the error message list
        for (ObjectError error : result.getAllErrors()) {
            errorMessages.append("\n- ").append(error.getDefaultMessage());
        }

        return new ResponseEntity<>(errorMessages.toString(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
