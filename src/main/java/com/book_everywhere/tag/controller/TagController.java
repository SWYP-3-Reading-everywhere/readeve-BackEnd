package com.book_everywhere.tag.controller;

import com.book_everywhere.tag.service.TagService;
import com.book_everywhere.util.dto.CMRespDto;
import com.book_everywhere.tag.dto.TagDto;
import com.book_everywhere.tag.dto.TaggedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
        List<TagDto> result = tagService.모든태그조회();
        return new CMRespDto<>(HttpStatus.OK, result, "태그 조회 성공");
    }

    @GetMapping("/api/tags/top")
    public CMRespDto<?> findTagCount() {
        List<Page<TaggedDto>> result = tagService.핀의5개태그조회();
        return new CMRespDto<>(HttpStatus.OK, result, "태그 개수 조회 성공");
    }

}
