package com.book_everywhere.web;

import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.service.WebDataService;
import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.review.ReviewRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WebDataController {

    private final WebDataService webDataService;

    @GetMapping("/api/data/all")
    public CMRespDto<?> findAllData() {
        List<ReviewRespDto> result = webDataService.모든공유데이터가져오기();
        return new CMRespDto<>(HttpStatus.OK, result,"모든 데이터 조회 성공");
    }

    @GetMapping("/api/tags/pinId")
    public CMRespDto<?> findTagsInPin(@PathVariable Long pinId) {
        List<Tagged> result = webDataService.findAllTaggedList(pinId);
        return new CMRespDto<>(HttpStatus.OK, result, "핀에 대한 모든 태그 조회 성공");
    }
}
