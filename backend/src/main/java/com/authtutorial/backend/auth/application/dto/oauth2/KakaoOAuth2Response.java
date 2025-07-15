package com.authtutorial.backend.auth.application.dto.oauth2;

import java.util.Map;


public class KakaoOAuth2Response implements OAuth2Response {
    private final Map<String, Object> attribute;

    public KakaoOAuth2Response(final Map<String, Object> attribute) {
        this.attribute = attribute;

    }

    @Override
    public String getProvider() {
        return "KAKAO";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return ((Map<String, Object>) attribute.get("kakao_account")).get("email").toString();
    }
}
