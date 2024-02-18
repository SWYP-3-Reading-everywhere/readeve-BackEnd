package com.book_everywhere.web.dto.review;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.web.dto.book.BookDto;
import lombok.Data;


@Data
public class ReviewDto {


    private Long id;
    private String title;
    private String content;
    private boolean isPrivate;
    private BookDto bookDto;

    public boolean getIsPrivate() {
        return isPrivate;
    }
}
