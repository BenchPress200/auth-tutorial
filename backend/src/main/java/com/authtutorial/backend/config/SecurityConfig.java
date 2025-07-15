package com.authtutorial.backend.config;

import com.authtutorial.backend.auth.application.config.JwtFilter;
import com.authtutorial.backend.auth.application.config.LoginFilter;
import com.authtutorial.backend.auth.application.config.oauth2.CustomOAuth2UserService;
import com.authtutorial.backend.auth.application.config.oauth2.OAuth2LoginSuccessHandler;
import com.authtutorial.backend.auth.domain.AuthTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig {
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final LogoutHandler logoutHandler;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final AuthTokenService authTokenService;
    private final ObjectMapper objectMapper;
    private final JwtFilter jwtFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public OAuth2AuthorizationRequestResolver oAuth2AuthorizationRequestResolver() {
        return new CustomOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 로그인 필터생성 및 엔드포인트 커스텀
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), authTokenService,
                objectMapper);
        loginFilter.setFilterProcessesUrl("/api/v1/auth/login");

        http // jwt 인증방식을 위한 기본 설정
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http // OAuth2 로그인 설정
                .oauth2Login((oauth2) -> oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .authorizationRequestResolver(
                                        oAuth2AuthorizationRequestResolver()
                                )
                        )
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(oAuth2LoginSuccessHandler));

        http // 인증-인가 API 설정
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/v1/users", "/api/v1/auth/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN") // 실제 UserDetails를 통해 조회하는 role은 "ROLE_ADMIN"
                        .anyRequest().authenticated());

        http // 로그아웃 설정
                .logout((logout) -> logout
                        .logoutUrl("/api/v1/auth/logout") // 로그아웃을 처리할 URL
                        .addLogoutHandler(logoutHandler) // 로그아웃 핸들러 추가 (여러 개 축 가능)
                        .logoutSuccessHandler(logoutSuccessHandler) // 로그아웃 성공 핸들러 추가
                );

        http // 인증-인가 예외 처리 설정
                .exceptionHandling((ex) -> ex
                        .authenticationEntryPoint(
                                authenticationEntryPoint) // 401 예외 처리 커스텀 (안하면 401 예외인데 403 던지는 기본값 객체 사용할 때도 있음)
                        .accessDeniedHandler(accessDeniedHandler)
                ); // 403 예외 처리 커스텀

        http // 필터 등록
                .addFilterBefore(jwtFilter, LoginFilter.class) // JWT 인증 필터 추가
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class); // 로그인 필터 추가

        return http.build();
    }
}
