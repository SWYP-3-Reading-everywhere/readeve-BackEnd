package com.book_everywhere.book.dto;

import com.book_everywhere.book.entity.Book;
import com.book_everywhere.user.entity.User;
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

    public Book toEntity(User user) {
        return Book.builder()
                .user(user)
                .author(author)
                .title(title)
                .coverImageUrl(thumbnail)
                .isbn(isbn)
                .isComplete(isComplete)
                .build();
    }
}
