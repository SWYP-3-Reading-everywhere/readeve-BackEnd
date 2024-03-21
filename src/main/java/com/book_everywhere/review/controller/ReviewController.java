package com.book_everywhere.review.controller;


import com.book_everywhere.book.service.BookService;
import com.book_everywhere.pin.service.PinService;
import com.book_everywhere.pin.service.VisitService;
import com.book_everywhere.review.service.ReviewService;
import com.book_everywhere.tag.service.TaggedService;
import com.book_everywhere.common.dto.CMRespDto;
import com.book_everywhere.review.dto.ReviewDto;
import com.book_everywhere.review.dto.ReviewRespDto;
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
    private final TaggedService taggedService;
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
        pinService.핀생성(reviewRespDto);
        bookService.책생성(reviewRespDto);
        taggedService.태그등록(reviewRespDto);
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

    @PutMapping("/api/review/edit/{reviewId}")
    @Operation(summary = "독후감 수정", description = "독후감을 수정합니다.")
    public CMRespDto<?> updateReview(@PathVariable Long reviewId,
                                     @RequestParam String prevBookTitle,
                                     @RequestParam String prevAddress,
                                     @RequestBody ReviewRespDto reviewRespDto) {
        reviewService.등록또는수정전예외처리(reviewRespDto);
        pinService.핀생성(reviewRespDto);
        bookService.책생성(reviewRespDto);
        taggedService.태그삭제(reviewRespDto.getPinRespDto().getAddress(), reviewRespDto.getSocialId());
        taggedService.태그등록(reviewRespDto);
        visitService.독후감쓰기전방문등록또는수정(reviewRespDto);
        reviewService.독후감수정(reviewId, reviewRespDto);
        reviewService.유저독후감개수검증후책삭제(reviewRespDto.getSocialId(),prevBookTitle);
        reviewService.독후감개수검증후핀삭제(prevAddress, reviewRespDto.getSocialId());
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 수정 완료");
    }

    @GetMapping("/api/review/public")
    public CMRespDto<?> findPublicReviews() {
        List<ReviewDto> result = reviewService.모든공유독후감조회();
        return new CMRespDto<>(HttpStatus.OK, result, "모든 공유 독후감 조회 완료");
    }

    @DeleteMapping("/api/review/delete/{reviewId}")
    public CMRespDto<?> deleteReview(@PathVariable Long reviewId,
                                     @RequestParam Long socialId,
                                     @RequestParam String bookTitle,
                                     @RequestParam String address) {
        reviewService.독후감삭제(reviewId);
        taggedService.태그삭제(address, socialId);
        reviewService.유저독후감개수검증후책삭제(socialId, bookTitle);
        reviewService.독후감개수검증후핀삭제(address, socialId);
        return new CMRespDto<>(HttpStatus.OK, null, "독후감 삭제 완료");
    }
}