package com.book_everywhere.web;


import com.book_everywhere.service.*;
import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.pin.PinDto;
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
    @PostMapping("/api/review")
    public CMRespDto<?> addReview(@RequestBody ReviewRespDto reviewRespDto) {
        pinService.핀생성(reviewRespDto); //null처리 완료
        bookService.책생성하기(reviewRespDto);// null처리 완료
        tagService.태그등록(reviewRespDto); // null처리 완료
        visitService.독후감쓰기전방문등록(reviewRespDto); // null처리 완료
        reviewService.독후감생성하기(reviewRespDto);
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 추가 완료");
    }

    //조회
    //공개 독후감 조회
    @GetMapping("/api/public/review")
    public CMRespDto<?> publicReviews(@RequestParam int page,
                                @RequestParam int size,
                                @RequestParam boolean isPrivate) {
        Pageable pageable = PageRequest.of(page, size);
        List<ReviewDto> result = reviewService.공유독후감조회(isPrivate, pageable);
        return new CMRespDto<>(HttpStatus.OK, result, "전체 공유 독후감 조회");
    }

    //수정
    @PutMapping("/api/review/{id}")
    public CMRespDto<?> updateReview(@PathVariable Long id, ReviewDto reviewDto) {
        reviewService.책업데이트(id, reviewDto);
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 수정 완료");
    }



}
