package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.tagged.TaggedRepository;
import com.book_everywhere.web.dto.exception.customs.CustomErrorCode;
import com.book_everywhere.web.dto.exception.customs.EntityNotFoundException;
import com.book_everywhere.web.dto.pin.PinDto;
import com.book_everywhere.web.dto.pin.PinRespDto;
import com.book_everywhere.web.dto.review.ReviewRespDto;
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
                .map(pin -> new PinDto(pin.getId(), pin.getPlaceId(), pin.getLatitude(), pin.getLongitude(), pin.getTitle(), pin.getAddress(), pin.getUrl(), pin.getCreateAt()))
                .toList();

        return resultDto;
    }

    @Transactional
    public void 핀생성(ReviewRespDto reviewRespDto) {
        PinRespDto pinRespDto = reviewRespDto.getPinRespDto();
        Pin pined = pinRepository.mFindPinByAddress(reviewRespDto.getPinRespDto().getAddress());
        if (pined == null) {
            Pin pin = Pin.builder()
                    .placeId(pinRespDto.getPlaceId())
                    .title(pinRespDto.getName())
                    .address(pinRespDto.getAddress())
                    .longitude(pinRespDto.getX())
                    .latitude(pinRespDto.getY())
                    .url(pinRespDto.getUrl())
                    .build();
            pinRepository.save(pin);
        }
    }

    @Transactional(readOnly = true)
    public List<PinDto> 나만의지도조회(Long userId) {
        List<Pin> init = pinRepository.mUserMap(userId);

        List<PinDto> resultDto = init.stream()
                .map(pin -> new PinDto(pin.getId(), pin.getPlaceId(), pin.getLatitude(), pin.getLongitude(), pin.getTitle(), pin.getAddress(), pin.getUrl(), pin.getCreateAt()))
                .toList();

        return resultDto;
    }

    @Transactional(readOnly = true)
    public List<PinDto> 태그조회(String tagContent) {
        List<Pin> init = taggedRepository.mFindTaggedPin(tagContent);
        if (init.isEmpty()) {
            throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
        }

        List<PinDto> resultDto = init.stream()
                .map(pin -> new PinDto(pin.getId(), pin.getPlaceId(), pin.getLatitude(), pin.getLongitude(), pin.getTitle(), pin.getAddress(), pin.getUrl(), pin.getCreateAt()))
                .toList();

        return resultDto;
    }
}
