package com.book_everywhere.book.dto;

import com.book_everywhere.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class BookDto {

    private String title;
    private String coverImageUrl;
    private String isbn;
    private Timestamp createdAt;
}
