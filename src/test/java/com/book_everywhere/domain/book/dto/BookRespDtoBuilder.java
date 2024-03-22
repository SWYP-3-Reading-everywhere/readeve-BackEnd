package com.book_everywhere.domain.book.dto;

import com.book_everywhere.book.dto.BookRespDto;
import com.book_everywhere.book.entity.Book;

public class BookRespDtoBuilder {

    public static BookRespDto createDefault() {
        return new BookRespDto(
                "Default ISBN", // isbn 기본값
                "Default Title", // title 기본값
                "http://defaultthumbnail.com/image.jpg", // thumbnail 기본값
                false, // isComplete 기본값
                "Default Author" // author 기본값
        );
    }

    public static Book toEntity() {
        return createDefault().toEntity();
    }
}
