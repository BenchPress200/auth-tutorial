package com.authtutorial.backend.auth.application.config.oauth2;

import com.authtutorial.backend.auth.application.dto.AuthResponse;
import com.authtutorial.backend.auth.application.dto.oauth2.OAuth2UserAuthQuery;
import com.authtutorial.backend.auth.domain.AuthTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AuthTokenService authTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication
    ) throws IOException {
        OAuth2UserAuthQuery oAuth2UserAuthQuery = (OAuth2UserAuthQuery) authentication.getPrincipal();
        long userId = oAuth2UserAuthQuery.getUserId();
        String role = oAuth2UserAuthQuery.getRole();
        String token = authTokenService.issueToken(userId, role);

        // 공백을 위한 인코딩
        token = URLEncoder.encode(token, StandardCharsets.UTF_8);

        Cookie cookie = new Cookie("Authorization", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

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

        // 콜백 컴포넌트 만들고
        response.sendRedirect("http://localhost:3000/oauth2/callback");
    }
}
