package com.book_everywhere.service;

import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.web.dto.review.ReviewDto;
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

    //사용자 검증에 메소드

    //등록
    @Transactional
    public Review createReview(ReviewDto reviewDto) {
        Review review = new Review().createReview(reviewDto);
        review.setIsPrivate(reviewDto.getIsPrivate());
        return reviewRepository.save(review);
    }


    //수정
    @Transactional
    public Review updateReview(Long id, ReviewDto reviewDto) {
        Review review = findOneReview(id);
        Review updateReview = review.update(reviewDto);
        return reviewRepository.save(updateReview);
    }



    //삭제
    @Transactional
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }


    //조회
    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }

    public Review findOneReview(Long id) {
        return reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review does not exist"));
    }
}
