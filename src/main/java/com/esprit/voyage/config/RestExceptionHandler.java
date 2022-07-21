package com.esprit.voyage.config;


import com.esprit.voyage.services.exception.CBException;
import com.esprit.voyage.services.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CBException erRes) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                erRes.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Add another exception handler .. to catch any exception
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception erRes) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                erRes.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}


