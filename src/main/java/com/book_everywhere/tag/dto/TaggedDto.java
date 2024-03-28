package com.book_everywhere.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaggedDto {
    private Long pinId;
    private String tagName;
    private int count;
    private LocalDateTime createdDate;
}
