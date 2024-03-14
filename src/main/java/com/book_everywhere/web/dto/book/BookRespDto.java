package com.book_everywhere.web.dto.book;

import com.book_everywhere.domain.book.Book;
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
                .isComplete(isComplete)
                .build();
    }
}
