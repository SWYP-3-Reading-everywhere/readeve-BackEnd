package com.book_everywhere.service;

import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.web.dto.review.ReviewDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Test
    void 공개_독후감_찾기() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setTitle("fsandlkfdsa");
        reviewDto.setContent("kfnasdlf");
        reviewDto.setPrivate(false);
        reviewService.createReview(reviewDto);

        ReviewDto reviewDto2 = new ReviewDto();
        reviewDto2.setTitle("fsandlkfdsa");
        reviewDto2.setContent("kfnasdlf");
        reviewDto2.setPrivate(false);
        reviewService.createReview(reviewDto2);

        ReviewDto reviewDto3 = new ReviewDto();
        reviewDto3.setTitle("fsandlkfdsa");
        reviewDto3.setContent("kfnasdlf");
        reviewDto3.setPrivate(true);
        reviewService.createReview(reviewDto3);

        Pageable pageable = PageRequest.of(0,10);

        List<Review> publicReviews = reviewService.findPublicReviews(false,pageable);
        List<Review> reviews = reviewService.findReviews();

        Assertions.assertThat(publicReviews.size()).isEqualTo(reviews.size()-1);
    }

    @Test
    void 독후감_수정() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setTitle("독후감");
        reviewDto.setContent("수정안함");
        reviewDto.setPrivate(false);
        Long reviewId = reviewService.createReview(reviewDto);

        Review findReview = reviewService.findOneReview(reviewId);
        ReviewDto reviewDto2 = new ReviewDto();
        reviewDto2.setTitle("독후감 제목");
        reviewDto2.setContent("수정안함");
        reviewDto2.setPrivate(true);
        reviewService.updateReview(findReview.getId(),reviewDto2);

        Review findUpdateReview = reviewService.findOneReview(reviewId);

        Assertions.assertThat(findUpdateReview.getTitle()).isEqualTo("독후감 제목");
        Assertions.assertThat(findUpdateReview.getContent()).isEqualTo("수정안함");
        Assertions.assertThat(findUpdateReview.isPrivate()).isEqualTo(true);
    }


}