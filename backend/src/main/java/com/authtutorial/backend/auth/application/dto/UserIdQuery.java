package com.authtutorial.backend.auth.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserIdQuery {
    private long userId;

    public static UserIdQuery of(final long userId) {
        return UserIdQuery.builder()
                .userId(userId)
                .build();
    }
}
