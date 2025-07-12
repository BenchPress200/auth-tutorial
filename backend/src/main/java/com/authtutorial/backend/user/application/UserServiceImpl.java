package com.authtutorial.backend.user.application;

import com.authtutorial.backend.user.application.dto.RegisterCommand;
import com.authtutorial.backend.user.application.dto.UserDetailsResponse;
import com.authtutorial.backend.user.domain.entity.User;
import com.authtutorial.backend.user.domain.exception.UserException;
import com.authtutorial.backend.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(final RegisterCommand registerCommand) {
        String name = registerCommand.getName();
        String password = registerCommand.getPassword();

        // 아이디 중복검사
        if (userRepository.existsByName(name)) {
            throw new UserException("Duplicated name", HttpStatus.CONFLICT);
        }

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(password);

        // 새 유저 엔티티 생성
        User newUser = registerCommand.toEntity(encodedPassword);

        // 회원가입
        userRepository.save(newUser);
    }

    @Override
    public UserDetailsResponse getUserDetails(final long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User with [" + userId + "] not found.", HttpStatus.NOT_FOUND));

        return UserDetailsResponse.from(user);
    }
}
