package com.learn.workflow.exception;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationError(MethodArgumentNotValidException ex) {
		log.error("Error MethodArgumentNotValidException with message {}",ex.getMessage(),ex);
        return ResponseEntity.badRequest().body(
            Map.of("error", "INVALID_REQUEST")
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> genericError(Exception ex) {
    	log.error("Error Exception with message {}",ex.getMessage(),ex);
        return ResponseEntity.status(500).body(
            Map.of("error", "INTERNAL_ERROR")
        );
    }
}
