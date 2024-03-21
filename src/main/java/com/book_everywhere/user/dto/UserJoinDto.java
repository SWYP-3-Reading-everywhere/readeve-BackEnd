package com.book_everywhere.user.dto;

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
