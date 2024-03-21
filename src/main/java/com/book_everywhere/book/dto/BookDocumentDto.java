package com.book_everywhere.book.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookDocumentDto {
    private String title;
    private String thumbnail;
    private List<String> authors;
    private String publisher;
    private String isbn;
    private String contents;
}
