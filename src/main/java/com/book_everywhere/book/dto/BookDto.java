package com.book_everywhere.book.dto;

import com.book_everywhere.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookDto {

    private String title;
    private String coverImageUrl;
    private String isbn;
    private LocalDateTime createdDate;

    public static BookDto toDto(Book book){
        return new BookDto(book.getTitle(),
                book.getCoverImageUrl(),
                book.getIsbn(),
                book.getCreatedDate());
    }
}
