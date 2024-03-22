package com.book_everywhere.domain.review.dto;

import com.book_everywhere.review.dto.ReviewRespDto;
import com.book_everywhere.book.dto.BookRespDto;
import com.book_everywhere.pin.dto.PinRespDto;

import java.util.List;

public class ReviewRespDtoTestBuilder {


    public static ReviewRespDto createDefault(PinRespDto pinRespDto, BookRespDto bookRespDto) {
        return new ReviewRespDto(
                123456L,
                "Default Review Title",
                "Default Author",
                true,
                pinRespDto,
                bookRespDto,
                List.of("Default Tag"),
                "Default Review Content"
        );
    }


}
