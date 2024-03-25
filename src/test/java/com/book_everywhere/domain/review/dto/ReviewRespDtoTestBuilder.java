package com.book_everywhere.domain.review.dto;

import com.book_everywhere.domain.book.dto.BookRespDtoBuilder;
import com.book_everywhere.domain.pin.dto.PinRespDtoTestBuilder;
import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.book.entity.Book;
import com.book_everywhere.review.dto.ReviewRespDto;
import com.book_everywhere.book.dto.BookRespDto;
import com.book_everywhere.pin.dto.PinRespDto;
import com.book_everywhere.review.entity.Review;
import com.book_everywhere.auth.entity.User;

import java.util.List;

public class ReviewRespDtoTestBuilder {


    public static ReviewRespDto createDefault(PinRespDto pinRespDto, BookRespDto bookRespDto) {
        return new ReviewRespDto(
                123456L,
                "Default Review Title",
                "Default Author",
                true,
                pinRespDto,
                bookRespDto,
                List.of("Default Tag"),
                "Default Review Content"
        );
    }

    public static Review createReviewEntity(Pin pin, Book book, User user) {
        // ReviewRespDto 객체 생성
        ReviewRespDto reviewRespDto = ReviewRespDtoTestBuilder.createDefault(PinRespDtoTestBuilder.createDefault(), BookRespDtoBuilder.createDefault());

        // ReviewRespDto 객체의 toEntity 메서드를 사용하여 Review 엔티티 객체 생성
        Review review = reviewRespDto.toEntity(book, pin, user);

        return review;
    }

}
