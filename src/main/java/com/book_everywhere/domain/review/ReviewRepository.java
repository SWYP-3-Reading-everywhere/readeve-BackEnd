package com.book_everywhere.domain.review;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    //공유목록에서의 모든 독후감 조회
    List<Review> findByIsPrivateOrderByCreateAtDesc(boolean isPrivate, Pageable pageable);

    // 개인지도에서 핀을 눌렀을때 독후감이 모두 뜨는 기능
    // Entity 바뀐다면 수정 필요 -> 수정완료
    @Query("SELECT review FROM Review review WHERE review.book.user.socialId = :socialId AND review.pin.id = :pinId ORDER BY review.createAt DESC")
    List<Review> mFindReviewUserMap(@Param("socialId") Long socialId, @Param("pinId") Long pinId);

    // socialId를 통한 모든 독후감 생성
    @Query("SELECT r FROM Review r WHERE r.book.user.socialId = :userId ORDER BY r.createAt DESC")
    List<Review> mFindReviewsByUser(@Param("userId") Long userId);

    @Query("SELECT r FROM Review r WHERE r.book.id = :bookId ORDER BY r.createAt DESC")
    List<Review> mFindReviewsByBook(@Param("bookId") Long bookId);

    //독후감 삭제
    @Modifying
    @Query("DELETE FROM Review WHERE id = :reviewId")
    int mDeleteReview(@Param("reviewId") Long reviewId);
}
