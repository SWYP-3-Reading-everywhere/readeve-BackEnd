package com.book_everywhere.web.dto.review;

import com.book_everywhere.domain.book.Book;
import lombok.Data;


@Data
public class ReviewDto {

    String title;
    String content;
    boolean isPrivate;
    Book book;

    public boolean getIsPrivate() {
        return isPrivate;
    }
}
