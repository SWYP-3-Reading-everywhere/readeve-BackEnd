package com.book_everywhere.web;

import com.book_everywhere.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/**") // 모든 요청을 처리합니다.
public class UniversalController {

    @GetMapping
    public CMRespDto<?> handleRequest() {
        return new CMRespDto<>(HttpStatus.OK, null, "존재하지 않는 API입니다.");
    }

}
