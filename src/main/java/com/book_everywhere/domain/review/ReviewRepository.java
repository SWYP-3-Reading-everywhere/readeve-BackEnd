package com.book_everywhere.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    // 개인지도에서 핀을 눌렀을때 독후감이 모두 뜨는 기능
    // Entity 바뀐다면 수정 필요 -> 수정완료
    @Query("SELECT review FROM Review review WHERE review.book.user.socialId = :socialId AND review.pin.id = :pinId")
    List<Review> mFindReviewUserMap(@Param("socialId") Long socialId, @Param("pinId") Long pinId);

    //모든 공유리뷰 호출
    @Query("SELECT review FROM Review review WHERE review.isPrivate = false")
    List<Review> mFindAllPublicReviews();

    //독후감 삭제
    @Modifying
    @Query("DELETE FROM Review WHERE id = :reviewId")
    int mDeleteReview(@Param("reviewId") Long reviewId);

}
