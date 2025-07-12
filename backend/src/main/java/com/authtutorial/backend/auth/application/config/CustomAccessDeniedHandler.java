package com.authtutorial.backend.auth.application.config;

import com.authtutorial.backend.auth.application.dto.AuthResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        AuthResponse authResponse = AuthResponse.builder()
                .status(HttpServletResponse.SC_FORBIDDEN)
                .message("Forbidden")
                .build();

        String jsonString = objectMapper.writeValueAsString(
                authResponse
        );

        response.getWriter().write(jsonString);
    }
}
