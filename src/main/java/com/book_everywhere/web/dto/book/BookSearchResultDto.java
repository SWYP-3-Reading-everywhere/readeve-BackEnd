package com.book_everywhere.web.dto.book;

import lombok.Data;

import java.util.List;
@Data
public class BookSearchResultDto {
    private List<BookDocumentDto> documents;
}
