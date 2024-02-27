package com.book_everywhere.web.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagCountDto {
    private String content;
    private Long count;
}
