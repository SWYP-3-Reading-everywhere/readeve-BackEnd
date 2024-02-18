package com.book_everywhere.domain.review;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    //공유목록에서의 모든 독후감 조회
    List<Review> findByIsPrivate(boolean isPrivate, Pageable pageable);

    //특정 유저의 모든 독후감 조회
    List<Review> findByUserAndBook(User user, Book book);

    // 개인지도에서 핀을 눌렀을때 독후감이 모두 뜨는 기능
    @Query("SELECT review FROM Review review WHERE review.book.user.socialId = :socialId AND review.pin.id = :pinId")
    List<Review> mFindReviewUserMap(@Param("socialId") Long socialId, @Param("pinId") Long pinId);
}
