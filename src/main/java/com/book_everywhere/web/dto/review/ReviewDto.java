package com.book_everywhere.web.dto.review;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.pin.Pin;
import lombok.Data;


@Data
public class ReviewDto {

    String title;
    String content;
    boolean isPrivate;

    public boolean getIsPrivate() {
        return isPrivate;
    }
}
