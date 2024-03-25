package com.book_everywhere.domain.book.repository;

import com.book_everywhere.auth.entity.User;
import com.book_everywhere.auth.repository.UserRepository;
import com.book_everywhere.book.entity.Book;
import com.book_everywhere.book.repository.BookRepository;
import com.book_everywhere.domain.auth.UserTestBuilder;
import com.book_everywhere.domain.book.dto.BookRespDtoBuilder;
import com.book_everywhere.domain.pin.dto.PinRespDtoTestBuilder;
import com.book_everywhere.domain.review.dto.ReviewRespDtoTestBuilder;
import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.pin.repository.PinRepository;
import com.book_everywhere.review.entity.Review;
import com.book_everywhere.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PinRepository pinRepository;

    @DisplayName("Repository_유저이름으로_책_찾아오기_테스트")
    @Test
    void mFindBookByUserId() {
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
        List<Book> foundBook = bookRepository.mFindBookByUserId(user.getSocialId());

        //then
        assertEquals(review.getBook().getId(), foundBook.get(0).getId());
        assertThat(foundBook.size()).isEqualTo(1);
    }

    @DisplayName("Repository_Isbn으로_책_찾아오기_테스트")
    @Test
    void mFindBookIsbn() {
    }

    @DisplayName("Repository_제목으로_책_찾아오기_테스트")
    @Test
    void mFindBookTitle() {
    }
}