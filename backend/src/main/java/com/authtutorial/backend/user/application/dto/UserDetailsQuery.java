package com.authtutorial.backend.user.application.dto;

import com.authtutorial.backend.user.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDetailsQuery {
    private long id;
    private String name;
    private String provider;

    public static UserDetailsQuery from(final User user) {
        return UserDetailsQuery.builder()
                .id(user.getId())
                .name(user.getName())
                .provider(user.getProvider())
                .build();
    }

}
