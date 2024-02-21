package com.book_everywhere.web;

import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.review.ReviewDto;
import com.book_everywhere.web.dto.user.UserJoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @GetMapping("/api/user")
    public CMRespDto<?> publicReviews(UserJoinDto userJoinDto){
        return new CMRespDto<>(HttpStatus.OK, userJoinDto, "회원 가입 성공!");
    }

}
