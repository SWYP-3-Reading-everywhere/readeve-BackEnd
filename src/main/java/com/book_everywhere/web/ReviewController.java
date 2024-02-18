package com.book_everywhere.web;


import com.book_everywhere.domain.review.Review;
import com.book_everywhere.service.ReviewService;
import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.review.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    //조회
    //공개 독후감 조회
    @GetMapping("/all/review")
    public CMRespDto<?> reviews(@RequestParam int page, @RequestParam int size, @RequestParam boolean isPrivate) {
        Pageable pageable = PageRequest.of(page, size);
        List<Review> result = reviewService.findPublicReviews(isPrivate,pageable);
        return new CMRespDto<>(HttpStatus.OK, result, "전체 공유 독후감 조회");
    }

    //추후 예정
    @GetMapping("/review")
    public CMRespDto<?> userReview() {
        //책에서 유저 아이디 값이 필요함

        return null;
    }

    //등록
    @PostMapping("/review")
    public CMRespDto<?> addReview(ReviewDto reviewDto) {
        reviewService.createReview(reviewDto);
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 추가 완료");
    }


    //수정
    @PutMapping("/review/{id}")
    public CMRespDto<?> updateReview(@PathVariable Long id, ReviewDto reviewDto) {
        reviewService.updateReview(id, reviewDto);
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 수정 완료");
    }

    //삭제
    @DeleteMapping("/review/{id}")
    public CMRespDto<?> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 삭제 완료");
    }


}
