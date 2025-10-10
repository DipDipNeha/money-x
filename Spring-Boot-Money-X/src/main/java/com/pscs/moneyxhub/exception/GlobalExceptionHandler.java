
package com.pscs.moneyxhub.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pscs.moneyxhub.model.ResponseData;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseData> handleResourceNotFoundException(ResourceNotFoundException ex) {
    	ResponseData response = new ResponseData();
    	response.setResponseCode("404");
    	response.setResponseMessage(ex.getMessage());
    	                  
    	
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData> handleGlobalException(Exception ex) {
    	ResponseData response = new ResponseData();
    	response.setResponseCode("500");
    	response.setResponseMessage("An unexpected error occurred: " + ex.getMessage());
    	
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
