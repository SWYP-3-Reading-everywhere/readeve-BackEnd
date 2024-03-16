package com.book_everywhere.web;


import com.book_everywhere.service.*;
import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.review.ReviewDto;
import com.book_everywhere.web.dto.review.ReviewRespDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/api/write")
    @Operation(summary = "독후감 추가", description = "독후감을 새로 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "독후감 추가 완료",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CMRespDto.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = @Content)
    })
    public CMRespDto<?> addReview(@RequestBody ReviewRespDto reviewRespDto) {
        reviewService.등록또는수정전예외처리(reviewRespDto);
        pinService.핀생성또는수정(reviewRespDto);
        bookService.책생성또는수정(reviewRespDto);
        tagService.태그등록또는수정(reviewRespDto);
        visitService.독후감쓰기전방문등록또는수정(reviewRespDto);
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

    @PutMapping("/api/write/{reviewId}")
    @Operation(summary = "독후감 수정", description = "독후감을 수정합니다.")
    public CMRespDto<?> updateReview(@PathVariable Long reviewId,@RequestBody ReviewRespDto reviewRespDto) {
        reviewService.등록또는수정전예외처리(reviewRespDto);
        pinService.핀생성또는수정(reviewRespDto);
        bookService.책생성또는수정(reviewRespDto);
        tagService.태그등록또는수정(reviewRespDto);
        visitService.독후감쓰기전방문등록또는수정(reviewRespDto);
        reviewService.독후감수정(reviewId, reviewRespDto);
        reviewService.유저독후감개수검증후책삭제(reviewId);
        reviewService.독후감개수검증후핀삭제(reviewId);
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 수정 완료");
    }

    @GetMapping("/api/review/public")
    public CMRespDto<?> findPublicReviews() {
        List<ReviewDto> result = reviewService.모든공유독후감조회();
        return new CMRespDto<>(HttpStatus.OK, result, "모든 공유 독후감 조회 완료");
    }
}
