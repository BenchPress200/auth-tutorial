package com.authtutorial.backend.user.application.dto;

import com.authtutorial.backend.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterCommand {
    private String name;
    private String password;

    public User toEntity(final String encodedPassword) {
        return User.builder()
                .name(name)
                .password(encodedPassword)
                .role("ROLE_USER")
                .provider("LOCAL")
                .build();
    }
}
