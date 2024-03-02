package com.book_everywhere.web.dto.review;

import com.book_everywhere.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private boolean isPrivate;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
