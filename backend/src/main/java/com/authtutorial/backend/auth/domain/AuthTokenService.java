package com.authtutorial.backend.auth.domain;

import org.springframework.security.core.AuthenticationException;

public interface AuthTokenService {
    String issueToken(long userId, String role);

    boolean isExpired(String token) throws AuthenticationException;

    long extractUserId(String token);

    String extractRole(String token);
}
