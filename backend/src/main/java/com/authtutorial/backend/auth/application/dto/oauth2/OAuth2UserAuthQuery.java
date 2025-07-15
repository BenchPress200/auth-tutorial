package com.authtutorial.backend.auth.application.dto.oauth2;

import com.authtutorial.backend.user.domain.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Builder
@RequiredArgsConstructor
public class OAuth2UserAuthQuery implements OAuth2User {
    private final long userId;
    private final String name;
    private final String password;
    private final String role;

    public static OAuth2UserAuthQuery from(final User user) {
        return OAuth2UserAuthQuery.builder()
                .userId(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> this.role);

        return collection;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.name;
    }
}
