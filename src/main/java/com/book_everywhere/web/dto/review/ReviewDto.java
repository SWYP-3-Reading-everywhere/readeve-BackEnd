package com.book_everywhere.web.dto.review;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String title;
    private String content;
    private boolean isPrivate;
    private Timestamp createAt;
    private Timestamp updateAt;
}
