package com.authtutorial.backend.auth.application.dto;

import com.authtutorial.backend.user.domain.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Builder
@RequiredArgsConstructor
public class UserAuthQuery implements UserDetails {
    private final long userId;
    private final String name;
    private final String password;
    private final String role;

    public static UserAuthQuery from(final User user) {
        return UserAuthQuery.builder()
                .userId(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> role);

        return collection;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
    }
}
