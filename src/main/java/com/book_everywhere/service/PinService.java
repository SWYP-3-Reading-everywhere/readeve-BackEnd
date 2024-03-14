package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.domain.tagged.TaggedRepository;
import com.book_everywhere.web.exception.customs.CustomErrorCode;
import com.book_everywhere.web.exception.customs.EntityNotFoundException;
import com.book_everywhere.web.dto.pin.PinDto;
import com.book_everywhere.web.dto.pin.PinRespDto;
import com.book_everywhere.web.dto.pin.PinWithTagCountRespDto;
import com.book_everywhere.web.dto.review.ReviewRespDto;
import com.book_everywhere.web.dto.tag.TagCountRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    public void 핀생성또는수정(ReviewRespDto reviewRespDto) {
        PinRespDto pinRespDto = reviewRespDto.getPinRespDto();
        Pin pined = pinRepository.mFindPinByAddress(reviewRespDto.getPinRespDto().getAddress());
        if (pined == null) {
            Pin pin = pinRespDto.toEntity();
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

    @Transactional(readOnly = true)
    public List<PinWithTagCountRespDto> 핀의상위5개태그개수와함께조회() {
        List<Pin> pins = pinRepository.findAll();
        return pins.stream().map(pin -> {
            PageRequest pageRequest = PageRequest.of(0, 5);
            List<Tagged> taggeds = taggedRepository.mFindPinWithTagCount(pin.getId(), pageRequest);
            List<TagCountRespDto> tagCountRespDtos = taggeds.stream()
                    .map(tagged -> new TagCountRespDto(
                            tagged.getTag().getContent(),
                            tagged.getCount())).toList();

            return new PinWithTagCountRespDto(
                    pin.getId(),
                    pin.getPlaceId(),
                    pin.getLatitude(),
                    pin.getLongitude(),
                    pin.getTitle(),
                    pin.getAddress(),
                    pin.getUrl(),
                    pin.getCreateAt(),
                    tagCountRespDtos
            );
        }).toList();
    }
}
