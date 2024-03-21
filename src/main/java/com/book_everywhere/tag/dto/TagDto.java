package com.book_everywhere.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagDto {
    private String content;
    private boolean isSelected;
    private String category;
}
