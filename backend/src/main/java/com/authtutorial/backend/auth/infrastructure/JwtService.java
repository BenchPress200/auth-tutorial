package com.authtutorial.backend.auth.infrastructure;

import com.authtutorial.backend.auth.domain.AuthTokenService;
import com.authtutorial.backend.auth.domain.exception.AuthException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class JwtService implements AuthTokenService {
    private static final String USER_ID_CLAIM_KEY = "userId";
    private static final String USER_ROLE_CLAIM_KEY = "role";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final String secret = "thisistestsecretasdasdasdqwdqwqwdzcacwdd";
    private final long EXPIRED_TIME = 1_000 * 60 * 60; // 1 hour
    private final SecretKey secretKey = new SecretKeySpec(
            secret.getBytes(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().getAlgorithm()
    );

    @Override
    public String issueToken(
            final long userId,
            final String role
    ) {
        long now = System.currentTimeMillis();

        String accessToken = Jwts.builder()
                .claim(USER_ID_CLAIM_KEY, userId) // 페이로드 추가
                .claim(USER_ROLE_CLAIM_KEY, role) // 페이로드 추가
                .issuedAt(new Date(now)) // 발행시각, UTC
                .expiration(new Date(now + EXPIRED_TIME)) // 만료시각
                .signWith(secretKey) // 서명
                .compact();

        return TOKEN_PREFIX + accessToken;
    }

    @Override
    public boolean isExpired(String token) throws AuthenticationException {
        token = token.split(" ")[1];

        try {
            Jwts
                    .parser()
                    .verifyWith(secretKey) // 서명 보안키 설정
                    .build() // 파싱을 위한 jwt 객체 생성
                    .parseSignedClaims(token);

            return false;
        } catch (ExpiredJwtException e) { // 만료시간 예외
            return true;
        } catch (JwtException e) {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public long extractUserId(String token) {
        token = token.split(" ")[1];

        try {
            return Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get(USER_ID_CLAIM_KEY, Long.class);
        } catch (ExpiredJwtException e) {
            return e.getClaims().get(USER_ID_CLAIM_KEY, Long.class);
        } catch (JwtException e) {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public String extractRole(String token) {
        token = token.split(" ")[1];

        try {
            return Jwts
                    .parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get(USER_ROLE_CLAIM_KEY, String.class);
        } catch (ExpiredJwtException e) {
            return e.getClaims().get(USER_ROLE_CLAIM_KEY, String.class);
        } catch (JwtException e) {
            throw new AuthException("Authentication failed.", HttpStatus.UNAUTHORIZED);
        }
    }
}
