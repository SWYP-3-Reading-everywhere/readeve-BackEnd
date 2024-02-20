package com.book_everywhere.web.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRespDto {
    private String isbn;
    private String title;
    private String thumbnail;
    private boolean isComplete;
}
