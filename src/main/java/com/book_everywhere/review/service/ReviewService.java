package com.book_everywhere.review.service;

import com.book_everywhere.review.dto.ReviewDto;
import com.book_everywhere.review.dto.ReviewRespDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

public interface ReviewService {
    Long 독후감생성하기(ReviewRespDto reviewRespDto);

    List<ReviewDto> 책에따른모든리뷰(Long bookId);

    List<ReviewDto> 모든독후감조회();

    void 등록또는수정전예외처리(ReviewRespDto reviewRespDto);

    ReviewDto 단일독후감조회(Long reviewId);

    void 독후감수정(Long reviewId, ReviewRespDto reviewRespDto);

    void 유저독후감개수검증후책삭제(String prevBookTitle);

    void 독후감개수검증후핀삭제(String prevAddress, Long socialId);

    List<ReviewDto> 모든공유독후감조회();

    void 독후감삭제(Long reviewId);

    List<ReviewDto> 유저모든독후감조회(OAuth2User oAuth2User);

    List<ReviewDto> 단일핀독후감조회(Long pinId, OAuth2User oAuth2User);
}
