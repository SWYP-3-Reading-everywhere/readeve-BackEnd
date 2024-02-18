package com.book_everywhere.web.dto.book;

import lombok.Data;

@Data
public class BookDto {

    private String title;
    private String coverImageUrl;
    private String author;
    private boolean isComplete;

    public boolean getIsComplete() {
        return isComplete;
    }
}
