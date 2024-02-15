package com.book_everywhere.config.auth;

import com.book_everywhere.domain.user.Role;
import com.book_everywhere.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Builder
@Slf4j
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String image;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return ofKakao("id", attributes);
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        return OAuthAttributes.builder()
                .nickname((String) properties.get("nickname"))
                .image((String) properties.get("profile_image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .image(image)
                .role(Role.ROLE_MEMBER)
                .build();
    }

}