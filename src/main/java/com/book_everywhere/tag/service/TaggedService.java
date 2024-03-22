package com.book_everywhere.tag.service;

import com.book_everywhere.review.dto.ReviewRespDto;
import com.book_everywhere.tag.dto.TagDto;

import java.util.List;

public interface TaggedService {
    void 태그등록(ReviewRespDto reviewRespDto);
    List<TagDto> 모든태그조회();
    void 태그삭제(String address, Long socialId);
}
