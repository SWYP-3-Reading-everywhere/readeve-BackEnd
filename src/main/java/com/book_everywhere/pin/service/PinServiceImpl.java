package com.book_everywhere.pin.service;

import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.pin.repository.PinRepository;
import com.book_everywhere.tag.repository.TaggedRepository;
import com.book_everywhere.pin.dto.PinDto;
import com.book_everywhere.pin.dto.PinRespDto;
import com.book_everywhere.pin.dto.PinWithTagCountRespDto;
import com.book_everywhere.review.dto.ReviewRespDto;
import com.book_everywhere.tag.dto.TagCountRespDto;
import com.book_everywhere.exception.customs.CustomErrorCode;
import com.book_everywhere.exception.customs.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PinServiceImpl implements PinService {
    private final PinRepository pinRepository;
    private final TaggedRepository taggedRepository;

    //DTO 변환단계
    public List<PinDto> 전체지도조회() {
        List<Pin> init = pinRepository.mFindAllPin();
        List<PinDto> resultDto = init.stream()
                .map(pin -> new PinDto(pin.getId(), pin.getPlaceId(), pin.getLatitude(), pin.getLongitude(), pin.getTitle(), pin.getAddress(), pin.getUrl(), pin.getCreateAt()))
                .toList();

        return resultDto;
    }

    public void 핀생성(ReviewRespDto reviewRespDto) {
        PinRespDto pinRespDto = reviewRespDto.getPinRespDto();
        Pin pined = pinRepository.mFindPinByAddress(reviewRespDto.getPinRespDto().getAddress());
        if (pined == null) {
            Pin pin = pinRespDto.toEntity();
            pinRepository.save(pin);
        }
    }

    public List<PinDto> 나만의지도조회(Long userId) {
        List<Pin> init = pinRepository.mUserMap(userId);

        List<PinDto> resultDto = init.stream()
                .map(pin -> new PinDto(pin.getId(), pin.getPlaceId(), pin.getLatitude(), pin.getLongitude(), pin.getTitle(), pin.getAddress(), pin.getUrl(), pin.getCreateAt()))
                .toList();

        return resultDto;
    }


    public List<PinDto> 태그조회(String tagContent) {
        List<Pin> init = pinRepository.mFindTaggedPin(tagContent);
        if (init.isEmpty()) {
            throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
        }

        List<PinDto> resultDto = init.stream()
                .map(pin -> new PinDto(pin.getId(), pin.getPlaceId(), pin.getLatitude(), pin.getLongitude(), pin.getTitle(), pin.getAddress(), pin.getUrl(), pin.getCreateAt()))
                .toList();

        return resultDto;
    }


    public List<PinWithTagCountRespDto> 핀의상위5개태그개수와함께조회() {
        List<Pin> pins = pinRepository.findAll();
        return pins.stream().map(pin -> {
            List<Object[]> taggeds = taggedRepository.mCountByPinId(pin.getId());
            List<TagCountRespDto> tagCountRespDtos = taggeds.stream()
                    .map(tagged -> new TagCountRespDto(
                            (String) tagged[0],
                            (Integer) tagged[1])).toList();

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

    public List<PinWithTagCountRespDto> 공유또는개인핀의상위5개태그개수와함께조회(boolean pinIsPrivate) {
        List<Pin> pins = pinRepository.mFindPinByIsPrivate(pinIsPrivate);
        return pins.stream().map(pin -> {
            List<Object[]> taggeds = taggedRepository.mCountByPinId(pin.getId());
            List<TagCountRespDto> tagCountRespDtos = taggeds.stream()
                    .map(tagged -> new TagCountRespDto(
                            (String) tagged[0],
                            (Integer) tagged[1])).toList();

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
