package com.book_everywhere.web.dto.user;

import com.book_everywhere.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Data
public class UserDto {
    private String nickname;

    private Map<String, Object> attr;

}
