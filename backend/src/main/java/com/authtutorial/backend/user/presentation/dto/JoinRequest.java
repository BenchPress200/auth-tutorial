package com.authtutorial.backend.user.presentation.dto;

import com.authtutorial.backend.user.application.dto.RegisterCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class JoinRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String password;

    public RegisterCommand toCommand() {
        return RegisterCommand.builder().
                name(name).
                password(password).
                build();
    }
}
