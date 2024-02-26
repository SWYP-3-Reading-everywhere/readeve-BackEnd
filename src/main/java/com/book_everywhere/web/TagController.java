package com.book_everywhere.web;

import com.book_everywhere.service.TagService;
import com.book_everywhere.web.dto.CMRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    @GetMapping("/api/tags")
    public CMRespDto<?> findAllTag() {
        List<String> 모든태그조회 = tagService.모든태그조회();
        return new CMRespDto<>(HttpStatus.OK, 모든태그조회, "태그 조회 성공");
    }
}
