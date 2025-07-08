package com.authtutorial.backend.exception;

import com.authtutorial.backend.user.domain.exception.UserException;
import com.benchpress200.apiresponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleDTOException() {
        return ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Invalid value.")
                .build();
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> handleUserException(final UserException e) {
        HttpStatus status = e.getHttpStatus();
        String message = e.getMessage();
        
        return ApiResponse.builder()
                .status(status)
                .message(message)
                .build();
    }
}
