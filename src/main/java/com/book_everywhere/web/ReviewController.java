package com.book_everywhere.web;


import com.book_everywhere.domain.review.Review;
import com.book_everywhere.service.*;
import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.review.ReviewDto;
import com.book_everywhere.web.dto.review.ReviewRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final PinService pinService;
    private final TagService tagService;
    private final VisitService visitService;
    private final BookService bookService;
    private final ReviewService reviewService;

    // 리뷰 등록 !!! 가장 중요 코드

    /**
     * pin 생성/등록기능
     * 최초등록시 book 등록기능
     * tagged 등록기능
     * 최초등록시 visit 등록기능
     * review 등록 기능
     */
    @PostMapping("/api/write")
    public CMRespDto<?> addReview(@RequestBody ReviewRespDto reviewRespDto) {
        pinService.핀생성(reviewRespDto);
        bookService.책생성하기(reviewRespDto);
        tagService.태그등록(reviewRespDto);
        visitService.독후감쓰기전방문등록(reviewRespDto);
        reviewService.독후감생성하기(reviewRespDto);
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 추가 완료");
    }

    //조회
    //공개 독후감 조회
    @GetMapping("/api/reviews")
    public CMRespDto<?> publicReviews(){
        List<ReviewDto> result = reviewService.모든독후감조회();
        return new CMRespDto<>(HttpStatus.OK, result, "전체 공유 독후감 조회");
    }

    @GetMapping("/api/detail/{bookId}")
    public CMRespDto<?> bookReviews(@PathVariable Long bookId) {
        List<ReviewDto> result = reviewService.책에따른모든리뷰(bookId);
        return new CMRespDto<>(HttpStatus.OK, result, "책에 따른 전체 독후감 조회");
    }

    //수정
    @GetMapping("/api/review/{reviewId}")
    public CMRespDto<?> getReview(@PathVariable Long reviewId) {
        reviewService.단일독후감조회(reviewId);
        return new CMRespDto<>(HttpStatus.OK, null, "단일 독후감 조회");
    }

    
    //수정
    @PutMapping("/api/review/{reviewId}")
    public CMRespDto<?> updateReview(@PathVariable Long reviewId, ReviewDto reviewDto) {
        reviewService.독후감업데이트(reviewId, reviewDto);
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 수정 완료");
    }

}
