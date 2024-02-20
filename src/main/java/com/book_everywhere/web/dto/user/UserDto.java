package com.book_everywhere.web.dto.user;

import lombok.Data;

import java.util.Map;

@Data
public class UserDto {
    private String nickname;
    private Map<String, Object> attr;

}
