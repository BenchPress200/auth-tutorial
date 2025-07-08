package com.authtutorial.backend.user.presentation.dto;

import com.authtutorial.backend.user.application.dto.UserDetailsQuery;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDetailsResponse {
    private long id;
    private String name;
    private String provider;

    public static UserDetailsResponse from(final UserDetailsQuery userDetailsQuery) {
        return UserDetailsResponse.builder()
                .id(userDetailsQuery.getId())
                .name(userDetailsQuery.getName())
                .provider(userDetailsQuery.getProvider())
                .build();
    }
}
