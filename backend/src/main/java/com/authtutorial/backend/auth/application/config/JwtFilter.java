package com.authtutorial.backend.auth.application.config;

import com.authtutorial.backend.auth.application.dto.UserAuthQuery;
import com.authtutorial.backend.auth.domain.AuthTokenService;
import com.authtutorial.backend.user.domain.entity.User;
import com.authtutorial.backend.user.domain.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String COOKIE_AUTHORIZATION = "Authorization";

    private final AuthTokenService authTokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        String token = findToken(request);

        // 인증 APi가 아니라면 token == null -> 계속 필터 진행
        // 만료되었다면 필터 진행해서 AuthenticationEntryPoint 에서 처리
        if (token == null || authTokenService.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        long userId = authTokenService.extractUserId(token);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("Authentication failed: user not found"));
        UserAuthQuery userAuthQuery = UserAuthQuery.from(user);

        // 이미 인증된 사용자에 대한 인증 객체를 담는 것이기 때문에, credentials를 담을 필요없음 => 나중에 각 요청 쓰레드가 SecurityContext를 조회할 일이 있으면 꺼내볼 유저 객체만 할당
        Authentication authentication = new UsernamePasswordAuthenticationToken(userAuthQuery, null,
                userAuthQuery.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication); // 해당 요청 담당 쓰레드가 소유

        filterChain.doFilter(request, response);
    }

    private String findToken(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(COOKIE_AUTHORIZATION)) {
                return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
            }
        }

        return null;
    }
}

