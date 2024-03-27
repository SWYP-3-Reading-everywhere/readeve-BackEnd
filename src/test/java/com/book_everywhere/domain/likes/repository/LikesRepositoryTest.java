package com.book_everywhere.domain.likes.repository;

import com.book_everywhere.auth.entity.User;
import com.book_everywhere.auth.repository.UserRepository;
import com.book_everywhere.book.entity.Book;
import com.book_everywhere.book.repository.BookRepository;
import com.book_everywhere.domain.auth.UserTestBuilder;
import com.book_everywhere.domain.book.dto.BookRespDtoBuilder;
import com.book_everywhere.domain.pin.dto.PinRespDtoTestBuilder;
import com.book_everywhere.domain.review.dto.ReviewRespDtoTestBuilder;
import com.book_everywhere.likes.repository.LikesRepository;
import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.pin.repository.PinRepository;
import com.book_everywhere.review.entity.Review;
import com.book_everywhere.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class LikesRepositoryTest {
    @Autowired
    LikesRepository likesRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PinRepository pinRepository;

    @DisplayName("Repository_좋아요 등록 테스트")
    @Test
    void mLike() {
        //given
        User user = UserTestBuilder.createDefault();
        Book book = BookRespDtoBuilder.createDefault().toEntity();
        Pin pin = PinRespDtoTestBuilder.createDefault().toEntity();
        Review review = ReviewRespDtoTestBuilder.createReviewEntity(pin, book, user);
        userRepository.save(user);
        bookRepository.save(book);
        pinRepository.save(pin);
        reviewRepository.save(review);

        //when
        likesRepository.mLike(user.getId(), review.getId());

        //then
        long likeCount = likesRepository.countByReviewId(review.getId());
        assertThat(likeCount).isEqualTo(1L);

    }

    @DisplayName("Repository_좋아요_취소_테스트")
    @Test
    void mUnLike() {
        //given
        User user = UserTestBuilder.createDefault();
        Book book = BookRespDtoBuilder.createDefault().toEntity();
        Pin pin = PinRespDtoTestBuilder.createDefault().toEntity();
        Review review = ReviewRespDtoTestBuilder.createReviewEntity(pin, book, user);
        userRepository.save(user);
        bookRepository.save(book);
        pinRepository.save(pin);
        reviewRepository.save(review);

        //when
        likesRepository.mLike(user.getId(), review.getId());
        likesRepository.mUnLike(user.getId(), review.getId());

        long likeCount = likesRepository.countByReviewId(review.getId());
        assertThat(likeCount).isEqualTo(0L);
    }

    @DisplayName("Repository_좋아요_갯수_테스트")
    @Test
    void countByReviewId() {
        //given
        User user = UserTestBuilder.createDefault();
        Book book = BookRespDtoBuilder.createDefault().toEntity();
        Pin pin = PinRespDtoTestBuilder.createDefault().toEntity();
        Review review = ReviewRespDtoTestBuilder.createReviewEntity(pin, book, user);
        userRepository.save(user);
        bookRepository.save(book);
        pinRepository.save(pin);
        reviewRepository.save(review);

        //when
        likesRepository.mLike(user.getId(), review.getId());
        long likeCount = likesRepository.countByReviewId(review.getId());

        //then
        assertThat(likeCount).isEqualTo(1L);
    }

    @DisplayName("Repository_좋아요_상태_테스트")
    @Test
    void existsByReviewIdAndUserId() {
        //given
        User user = UserTestBuilder.createDefault();
        Book book = BookRespDtoBuilder.createDefault().toEntity();
        Pin pin = PinRespDtoTestBuilder.createDefault().toEntity();
        Review review = ReviewRespDtoTestBuilder.createReviewEntity(pin, book, user);
        userRepository.save(user);
        bookRepository.save(book);
        pinRepository.save(pin);
        reviewRepository.save(review);

        //when
        likesRepository.mLike(user.getId(), review.getId());
        boolean exists = likesRepository.existsByUserIdAndReviewId(user.getId(), review.getId());

        //then
        assertThat(exists).isTrue();
    }
}