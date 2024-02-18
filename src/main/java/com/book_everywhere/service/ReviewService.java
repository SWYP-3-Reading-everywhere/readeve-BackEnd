package com.book_everywhere.service;

import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.web.dto.review.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    //단일 핀을 눌렀을때 독후감이 조회됩니다.
    @Transactional(readOnly = true)
    public List<ReviewDto> 단일핀독후감조회(Long pinId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        List<Review> init = reviewRepository.mFindReviewUserMap((Long)oAuth2User.getAttributes().get("id"),pinId);

        List<ReviewDto> resultDto = init.stream()
                .map(review -> new ReviewDto(review.getId(),review.getTitle(),review.getContent(),review.isPrivate(),review.getUpdateAt(),review.getCreateAt()))
                .toList();

        return resultDto;
    }
}
