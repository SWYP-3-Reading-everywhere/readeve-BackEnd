package com.book_everywhere.service;

import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.web.dto.review.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    //사용자 검증에 메소드

    //등록
    @Transactional
    public Long createReview(ReviewDto reviewDto) {
        Review review = new Review().createFromDto(reviewDto);
        reviewRepository.save(review);
        return review.getId();
    }


    //수정
    @Transactional
    public void updateReview(Long id, ReviewDto reviewDto) {
        Review review = findOneReview(id);
        review.update(reviewDto);
    }


    //삭제
    @Transactional
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("Review does not exist");
        }
        reviewRepository.deleteById(id);
    }


    //조회
    //공유 지도에서의 조회
    public List<Review> findPublicReviews(boolean isPrivate, Pageable pageable) {
        return reviewRepository.findByIsPrivate(isPrivate, pageable);
    }

    //아이디 값 하나만 조회
    public Review findOneReview(Long id) {
        return reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review does not exist"));
    }

    //테스트용
    public List<Review> findReviews() {
        return reviewRepository.findAll();
    }
}
