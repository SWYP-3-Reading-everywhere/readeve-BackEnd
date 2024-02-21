package com.book_everywhere.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class UserJoinDto {
    private String nickname;
    private String image;
    private Long socialId;
}
