package com.book_everywhere.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    // 개인지도에서 핀을 눌렀을때 독후감이 모두 뜨는 기능
    @Query(value = "SELECT review FROM Review review WHERE review.book.user.socialId = :socialId AND review.book.pin.id = :pinId", nativeQuery = true)
    List<Review> mFindReviewUserMap(@Param("socialId") int socialId, @Param("pinId") int pinId);

    //모든 공유리뷰 호출
    @Query(value = "SELECT * FROM Review WHERE Review.isPrivate = false", nativeQuery = true)
    List<Review> mFindAllPublicReviews();

}
