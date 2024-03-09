package com.book_everywhere.web.dto.review;

import com.book_everywhere.web.dto.book.BookRespDto;
import com.book_everywhere.web.dto.pin.PinRespDto;
import com.book_everywhere.web.dto.tag.TagRespDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewRespDto {
    private Long socialId;
    //3월 2일 추가 모든 독후감 기록 페이지
    private String title;
    private String writer;
    private boolean isPrivate;
    private PinRespDto pinRespDto;
    private BookRespDto bookRespDto;
    private List<String> tags;
    private String content;
}
