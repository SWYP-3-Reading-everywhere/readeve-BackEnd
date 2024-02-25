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
    private String title;
    private boolean isPrivate;
    private PinRespDto pinRespDto;
    private BookRespDto bookRespDto;
    private List<TagRespDto> tags;
    private String content;
}
