package com.authtutorial.backend.user.presentation;

import com.authtutorial.backend.user.application.UserService;
import com.authtutorial.backend.user.application.dto.RegisterCommand;
import com.authtutorial.backend.user.presentation.dto.JoinRequest;
import com.benchpress200.apiresponse.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> join(@Valid @RequestBody final JoinRequest joinRequest) {
        RegisterCommand registerCommand = joinRequest.toCommand();
        userService.register(registerCommand);

        return ApiResponse.builder()
                .status(HttpStatus.CREATED)
                .message("Join completed.")
                .build();
    }
}
