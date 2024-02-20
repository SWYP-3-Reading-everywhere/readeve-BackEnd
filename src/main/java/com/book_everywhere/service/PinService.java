package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.domain.tag.TagRepository;
import com.book_everywhere.domain.tagged.TaggedRepository;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import com.book_everywhere.domain.visit.Visit;
import com.book_everywhere.web.dto.pin.PinDto;
import com.book_everywhere.web.dto.review.ReviewDto;
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
    private final TaggedRepository taggedRepository;

    //DTO 변환단계
    @Transactional(readOnly = true)
    public List<PinDto> 전체지도조회() {
        List<Pin> init = pinRepository.mFindAllPin();

        List<PinDto> resultDto = init.stream()
                .map(pin -> new PinDto(pin.getId(), pin.getLatitude(), pin.getLongitude(), pin.getTitle(), pin.getAddress(), pin.getCreateAt()))
                .toList();

        return resultDto;
    }

    @Transactional
    public void 핀생성(PinDto pinDto){
        Pin pin = pinDto.toEntity();
        pinRepository.save(pin);
    }

    @Transactional(readOnly = true)
    public List<PinDto> 나만의지도조회(@AuthenticationPrincipal OAuth2User oAuth2User){
        List<Pin> init = pinRepository.mUserMap((Long)oAuth2User.getAttributes().get("id"));

        List<PinDto> resultDto = init.stream()
                .map(pin -> new PinDto(pin.getId(), pin.getLatitude(), pin.getLongitude(), pin.getTitle(), pin.getAddress(), pin.getCreateAt()))
                .toList();

        return resultDto;
    }

    @Transactional(readOnly = true)
    public List<PinDto> 태그조회(String tagContent){
        List<Pin> init = taggedRepository.mFindTaggedPin(tagContent);

        List<PinDto> resultDto = init.stream()
                .map(pin -> new PinDto(pin.getId(), pin.getLatitude(), pin.getLongitude(), pin.getTitle(), pin.getAddress(), pin.getCreateAt()))
                .toList();

        return resultDto;
    }
}
