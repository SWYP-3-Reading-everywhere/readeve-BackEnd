package com.book_everywhere.service;

import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }

    public Review findOneReview(int id) {
        return reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review does not exist"));
    }
}
