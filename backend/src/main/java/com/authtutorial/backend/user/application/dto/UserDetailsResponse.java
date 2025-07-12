package com.authtutorial.backend.user.application.dto;

import com.authtutorial.backend.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDetailsResponse {
    private long id;
    private String name;
    private String provider;

    public static UserDetailsResponse from(final User user) {
        return UserDetailsResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .provider(user.getProvider())
                .build();
    }

}
