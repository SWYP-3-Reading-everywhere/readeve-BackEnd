package com.book_everywhere.review.dto;

import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.book.entity.Book;
import com.book_everywhere.review.entity.Review;
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
