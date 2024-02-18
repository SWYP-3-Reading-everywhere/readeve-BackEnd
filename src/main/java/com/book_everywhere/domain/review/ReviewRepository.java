package com.book_everywhere.domain.review;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByIsPrivate(boolean isPrivate, Pageable pageable);


}
