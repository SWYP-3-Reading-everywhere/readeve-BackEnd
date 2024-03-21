package com.book_everywhere.book.dto;

import com.book_everywhere.book.entity.Book;
import com.book_everywhere.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRespDto {
    private String isbn;
    private String title;
    private String thumbnail;
    private boolean isComplete;
    private String author;

    public Book toEntity() {
        return Book.builder()
                .author(author)
                .title(title)
                .coverImageUrl(thumbnail)
                .isbn(isbn)
                .build();
    }
}
