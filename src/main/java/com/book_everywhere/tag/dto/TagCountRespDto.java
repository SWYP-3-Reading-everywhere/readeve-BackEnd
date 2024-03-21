package com.book_everywhere.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagCountRespDto {
    private String content;
    private int count;
}
