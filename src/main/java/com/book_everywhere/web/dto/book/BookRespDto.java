package com.book_everywhere.web.dto.book;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.user.User;
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
