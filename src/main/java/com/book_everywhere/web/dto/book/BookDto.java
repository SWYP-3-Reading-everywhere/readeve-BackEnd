package com.book_everywhere.web.dto.book;

import com.book_everywhere.domain.book.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class BookDto {

    private Long userId;
    private String title;
    private String coverImageUrl;
    private String author;
    private boolean isComplete;
    private Timestamp createdAt;

    public boolean getIsComplete() {
        return isComplete;
    }

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .coverImageUrl(coverImageUrl)
                .author(author)
                .build();
    }
}
