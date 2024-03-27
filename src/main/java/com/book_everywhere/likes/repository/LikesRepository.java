package com.book_everywhere.likes.repository;

import com.book_everywhere.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {


    @Modifying
    @Query(value = "INSERT INTO likes(review_id, socialId) VALUES(:reviewId, :socialId)", nativeQuery = true)
    int mLike(Long socialId,Long reviewId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE review_id = :reviewId AND socialId = :socialId", nativeQuery = true)
    int mUnLike(Long socialId,Long reviewId);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.review.id = :reviewId")
    Long countByReviewId(@Param("reviewId") Long reviewId);

    boolean existsByReviewIdAndUserId(Long userId,Long reviewId);

}
