package com.book_everywhere.user.controller;

import com.book_everywhere.util.dto.CMRespDto;
import com.book_everywhere.user.dto.UserJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @GetMapping("/api/user")
    public CMRespDto<?> publicReviews(UserJoinDto userJoinDto){
        return new CMRespDto<>(HttpStatus.OK, userJoinDto, "회원 가입 성공!");
    }

}
