package com.authtutorial.backend.auth.application;

import com.authtutorial.backend.auth.application.dto.UserAuthQuery;
import com.authtutorial.backend.auth.application.dto.UserIdQuery;
import com.authtutorial.backend.user.domain.entity.User;
import com.authtutorial.backend.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElse(User.builder().build());

        return UserAuthQuery.from(user);
    }

    @Override
    public UserIdQuery whoAmI() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserAuthQuery userAuthQuery = (UserAuthQuery) authentication.getPrincipal();
        long userId = userAuthQuery.getUserId();
        return UserIdQuery.of(userId);
    }
}
