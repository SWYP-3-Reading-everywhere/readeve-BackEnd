package com.book_everywhere.domain.book;

import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface BookRepository extends JpaRepository<Book, Long> {

    //유저의 모든 리뷰 조회
    List<Review> findAllReviewsByUser(User user);

    //유저의 모든 책 조회
    List<Book> findAllByUser(User user);

}
