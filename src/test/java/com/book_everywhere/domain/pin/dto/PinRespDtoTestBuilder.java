package com.book_everywhere.domain.pin.dto;

import com.book_everywhere.book.entity.Book;
import com.book_everywhere.pin.dto.PinRespDto;
import com.book_everywhere.pin.entity.Pin;

public class PinRespDtoTestBuilder {
    public static PinRespDto createDefault() {
        return new PinRespDto(
                "Default Name",
                123.456,
                37.5665,
                126.9780,
                "Default Address",
                false,
                "http://default.url"
        );
    }

    public static Pin toEntity(){
        return createDefault().toEntity();
    }
}
