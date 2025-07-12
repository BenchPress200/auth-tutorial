package com.authtutorial.backend.auth.application.dto;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthResponse {
    private int status;
    private String message;
    private ZonedDateTime timestamp;

    @Builder
    public AuthResponse(
            final int status,
            final String message
    ) {
        this.status = status;
        this.message = message;
        this.timestamp = ZonedDateTime.now(ZoneOffset.UTC);
    }
}
