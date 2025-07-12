package com.authtutorial.backend.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {
    @Test
    void testValidateJwt() {
        String secret = "thisistestsecretasdasdasdqwdqwqwdzcacwdd";
        SecretKey secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        long EXPIRED_TIME = 60_000; // 1 minute
        long now = System.currentTimeMillis();
        long userId = 1;

        String token = Jwts.builder()
                .claim("userId", userId) // 페이로드 추가
                .issuedAt(new Date(now)) // 발행시각, UTC
                .expiration(new Date(now)) // 만료시각
                .signWith(secretKey) // 서명
                .compact();

        Assertions.assertThrows(ExpiredJwtException.class, () -> Jwts
                .parser()
                .verifyWith(secretKey) // 서명 보안키 설정
                .build() // 파싱을 위한 jwt 객체 생성
                .parseSignedClaims(token)
        );

        try {
            Jwts
                    .parser()
                    .verifyWith(secretKey) // 서명 보안키 설정
                    .build() // 파싱을 위한 jwt 객체 생성
                    .parseSignedClaims(token);
        } catch (RuntimeException e) {
            System.out.println(e.getClass());

        }
    }
}
