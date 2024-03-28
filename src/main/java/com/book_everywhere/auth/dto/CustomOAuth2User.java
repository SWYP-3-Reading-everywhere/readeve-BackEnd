package com.book_everywhere.auth.dto;

import com.book_everywhere.auth.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuthAttributes oAuthAttributes;
    private final Role role;

    public CustomOAuth2User(OAuthAttributes oAuth2Attributes, Role role) {

        this.oAuthAttributes = oAuth2Attributes;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return String.valueOf(role);
            }
        });

        return collection;
    }

    @Override
    public String getName() {
        return null;
    }
}
