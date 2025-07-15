package com.authtutorial.backend.auth.application.config.oauth2;

import com.authtutorial.backend.auth.application.dto.oauth2.KakaoOAuth2Response;
import com.authtutorial.backend.auth.application.dto.oauth2.OAuth2Response;
import com.authtutorial.backend.auth.application.dto.oauth2.OAuth2UserAuthQuery;
import com.authtutorial.backend.user.domain.entity.User;
import com.authtutorial.backend.user.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response;
        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoOAuth2Response(oAuth2User.getAttributes());

        } else {
            oAuth2Response = null;

        }

        String name = oAuth2Response.getEmail();

        // 여기까지 넘어왔다면 OAuth2인증 성공 유저임
        // 해당 유저의 이메일이 있다면 찾아서 로딩, 없다면 새로 가입후 로딩
        User user = userRepository.findByName(name).orElseGet(() ->
                userRepository.save(User.builder()
                        .name(name)
                        .password(UUID.randomUUID().toString())
                        .role("ROLE_USER")
                        .provider(oAuth2Response.getProvider())
                        .build())
        );
//        User user = userRepository.findByName(name).orElse(
//                userRepository.save(User.builder()
//                        .name(name)
//                        .password(UUID.randomUUID().toString())
//                        .role("ROLE_USER")
//                        .provider(oAuth2Response.getProvider())
//                        .build())
//        );

        return OAuth2UserAuthQuery.from(user);
    }
}
