package com.book_everywhere.web.dto.jwt;

import java.util.Map;

public class KakaoResponse implements OAuth2Response{

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
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
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");
        return properties.get("nickname").toString();
    }

    public String getImageUrl() {
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");
        return properties.get("profile_image").toString();
    }
}
