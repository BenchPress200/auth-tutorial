package com.authtutorial.backend.auth.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {
    private final HttpStatus httpStatus;

    public AuthException(
            final String message,
            final HttpStatus httpStatus
    ) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
