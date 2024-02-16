package com.book_everywhere.web.dto.user;

import com.book_everywhere.domain.user.Role;
import lombok.Data;

@Data
public class UserDto {

    private String nickname;
    private String role;
    private String socialId;
    private String ImageUrl;
}
