package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import com.book_everywhere.domain.visit.Visit;
import com.book_everywhere.web.dto.pin.PinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PinService {
    private final PinRepository pinRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public List<Pin> 전체지도조회() {
        return pinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Review> 단일핀조회(int pinId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        //단일 핀을 눌렀을때 독후감이 조회됩니다.
        return reviewRepository.mFindReviewUserMap((Long)oAuth2User.getAttributes().get("id"),pinId);
    }

    @Transactional
    public void 핀생성(PinDto pinDto){
        Pin pin = pinDto.toEntity();
        pinRepository.save(pin);
    }

    @Transactional(readOnly = true)
    public List<Pin> 나만의지도조회(@AuthenticationPrincipal OAuth2User oAuth2User){
        return pinRepository.mUserMap((Long)oAuth2User.getAttributes().get("id"));
    }
}
