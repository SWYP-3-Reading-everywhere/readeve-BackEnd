package com.book_everywhere.book.dto;

import com.book_everywhere.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class BookDto {

    private Long userId;
    private String title;
    private String coverImageUrl;
    private String isbn;
    private boolean isComplete;
    private Timestamp createdAt;

    public boolean getIsComplete() {
        return isComplete;
    }

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .coverImageUrl(coverImageUrl)
                .build();
    }
}
