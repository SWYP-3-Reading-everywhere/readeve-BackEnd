package com.book_everywhere.web.dto.jwt;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = (Map<String, Object>) attribute.get("kakao_account");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    public String getNickname() {
        return attribute.get("profile_nickname").toString();
    }

    public String getImageUrl() {
        return attribute.get("profile_image").toString();
    }
}
