package com.book_everywhere.review.dto;

import com.book_everywhere.book.dto.BookRespDto;
import com.book_everywhere.book.entity.Book;
import com.book_everywhere.auth.entity.User;
import com.book_everywhere.pin.dto.PinRespDto;
import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewRespDto {
    private Long socialId;
    //3월 2일 추가 모든 독후감 기록 페이지
    private String title;
    private String writer;
    private boolean isPrivate;
    private PinRespDto pinRespDto;
    private BookRespDto bookRespDto;
    private List<String> tags;
    private String content;

    public Review toEntity(Book book, Pin pin, User user){
        return Review.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .isPrivate(isPrivate)
                .user(user)
                .book(book)
                .pin(pin)
                .build();
    }
}
