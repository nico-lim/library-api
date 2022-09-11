package com.myrental.web;

import com.myrental.web.error.BadRequestException;
import com.myrental.web.error.ErrorException;
import com.myrental.web.error.NotFoundException;
import com.myrental.web.util.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ErrorException.class, NotFoundException.class, BadRequestException.class})
    public ResponseEntity<Object> handleErrorException(ErrorException ex, WebRequest request) {
        return ResponseEntity.status(ex.getHttpStatus()).body(ResponseHandler.generateResponse(
                ex.getMessage(),
                ex.getHttpStatus(),
                ex
        ));
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseHandler.generateResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex
        ));
    }
}
