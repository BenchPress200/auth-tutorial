package com.authtutorial.backend.auth.application.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {
    @Override
    public void logout(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Authentication authentication
    ) {
        // 이후에 리프레쉬 토큰 또는 로그인 정보가 추가된다면 정리하는 코드 필요
        Cookie tokenCookie = new Cookie("Authorization", null);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);

        response.addCookie(tokenCookie);
        log.info("로그아웃");
    }
}
