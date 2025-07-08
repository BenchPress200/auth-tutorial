package com.authtutorial.backend.user.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {
    private final HttpStatus httpStatus;

    public UserException(
            final String message,
            final HttpStatus httpStatus
    ) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
