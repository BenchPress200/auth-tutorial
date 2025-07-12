package com.authtutorial.backend.auth.presentation;

import com.authtutorial.backend.auth.application.AuthService;
import com.authtutorial.backend.auth.application.dto.UserIdQuery;
import com.benchpress200.apiresponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/whoami")
    public ResponseEntity<?> whoAmI() {
        UserIdQuery userIdQuery = authService.whoAmI();

        return ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("OK")
                .data(userIdQuery)
                .build();
    }
}
