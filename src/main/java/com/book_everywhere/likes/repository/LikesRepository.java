package com.book_everywhere.likes.repository;

import com.book_everywhere.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(review_id, user_id) VALUES(:reviewId, :userId)", nativeQuery = true)
    void mLike(@Param("userId") Long userId, @Param("reviewId") Long reviewId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE review_id = :reviewId AND user_id  = :userId", nativeQuery = true)
    void mUnLike(@Param("userId") Long userId, @Param("reviewId") Long reviewId);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.review.id = :reviewId")
    Long countByReviewId(@Param("reviewId") Long reviewId);

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);

}
