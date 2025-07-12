package com.authtutorial.backend.auth.application.config;

import com.authtutorial.backend.auth.application.dto.AuthResponse;
import com.authtutorial.backend.auth.application.dto.LoginCommand;
import com.authtutorial.backend.auth.application.dto.UserAuthQuery;
import com.authtutorial.backend.auth.domain.AuthTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        try {
            LoginCommand loginCommand = objectMapper.readValue(request.getInputStream(), LoginCommand.class);
            String name = loginCommand.getName();
            String password = loginCommand.getPassword();
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(name, password);

            return authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authentication
    ) throws IOException {
        UserAuthQuery userAuthQuery = (UserAuthQuery) authentication.getPrincipal();
        long userId = userAuthQuery.getUserId();
        String role = userAuthQuery.getRole();
        String token = authTokenService.issueToken(userId, role);

        response.addHeader("Authorization", token);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        AuthResponse authResponse = AuthResponse.builder()
                .status(HttpServletResponse.SC_OK)
                .message("Login completed successfully")
                .build();

        String jsonString = objectMapper.writeValueAsString(
                authResponse
        );

        response.getWriter().write(jsonString);
    }

    @Override
    protected void unsuccessfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException failed
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        AuthResponse authResponse = AuthResponse.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .message("Authentication failed")
                .build();

        String jsonString = objectMapper.writeValueAsString(authResponse);

        response.getWriter().write(jsonString);
    }
}
