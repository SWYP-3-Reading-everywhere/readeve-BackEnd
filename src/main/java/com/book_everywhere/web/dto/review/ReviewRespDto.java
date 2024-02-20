package com.book_everywhere.web.dto.review;

import com.book_everywhere.web.dto.book.BookDto;
import com.book_everywhere.web.dto.book.BookRespDto;
import com.book_everywhere.web.dto.pin.PinDto;
import com.book_everywhere.web.dto.pin.PinRespDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewRespDto {
    //이 부분 추가되어야함 유저의 소셜아이디
    private Long socialId;
    private String title;
    private boolean isPrivate;
    private PinRespDto pinRespDto;
    private BookRespDto bookRespDto;
    private List<String> tags;
    private String content;
}
