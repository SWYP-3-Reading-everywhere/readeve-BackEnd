package com.book_everywhere.tag.service;

import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.pin.repository.PinRepository;
import com.book_everywhere.tag.entity.Tag;
import com.book_everywhere.tag.repository.TagRepository;
import com.book_everywhere.tag.entity.Tagged;
import com.book_everywhere.tag.repository.TaggedRepository;
import com.book_everywhere.review.dto.ReviewRespDto;
import com.book_everywhere.tag.dto.TagDto;
import com.book_everywhere.tag.dto.TaggedDto;
import com.book_everywhere.exception.customs.CustomErrorCode;
import com.book_everywhere.exception.customs.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TaggedRepository taggedRepository;
    private final PinRepository pinRepository;
    private final EntityManager em;

    @Transactional
    public void 태그등록또는수정(ReviewRespDto reviewRespDto) {
        List<String> tags = reviewRespDto.getTags();
        if(tags.isEmpty()) {
            return;
        }
        for (String tagResp : tags) {
            Tag tag = tagRepository.mFindTagByName(tagResp);
            Pin pin = pinRepository.mFindPinByAddress(reviewRespDto.getPinRespDto().getAddress());

            Tagged tagged = taggedRepository.mFindTagged(tag.getId(), pin.getId());

            if (tagged == null) {
                Tagged newTagged = Tagged.builder()
                        .pin(pin)
                        .tag(tag)
                        .count(1)
                        .build();

                taggedRepository.save(newTagged);
            } else {
                tagged.changeTagged(pin, tag, tagged.getCount()+1);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<TagDto> 모든태그조회() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tag -> new TagDto(
                tag.getContent(),
                false,
                tag.getCategory().getName())).toList();
    }

    @Transactional(readOnly = true)
    public List<Page<TaggedDto>> 핀의5개태그조회() {
        List<Pin> pins = pinRepository.mFindAllPin();
        List<Page<TaggedDto>> tagFiveList = new ArrayList<>();
        for (Pin pin : pins) {
            Page<Tagged> taggeds = taggedRepository.findByPinIdOrderByCountDesc(pin.getId(), PageRequest.of(0, 5));
            Page<TaggedDto> taggedDtos = taggeds.map(tagged -> new TaggedDto(
                    tagged.getPin().getId(),
                    tagged.getTag().getContent(),
                    tagged.getCount(),
                    tagged.getCreateAt()
            ));
            tagFiveList.add(taggedDtos);
        }
        return tagFiveList;
    }

    @Transactional
    public void 태그삭제(List<String> tags, String address) {
        if(tags == null) {
            return;
        }
        //태그를 찾고
        for (String tagResp:
             tags) {
            Tag tag = tagRepository.mFindTagByName(tagResp);
            Pin pin = pinRepository.mFindPinByAddress(address);
            if(pin == null) {
                throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
            }
            Tagged tagged = taggedRepository.mFindTagged(tag.getId(), pin.getId());
            if(tagged.getCount()-1 == 0) {
                taggedRepository.delete(tagged);
            }
            tagged.changeTagged(pin, tag, tagged.getCount()-1);
        }
    }

    @Transactional
    public void 태그검증(Long socialId, String address) {
        Pin pin = pinRepository.mFindPinByAddress(address);
        if(pin == null) {
            throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
        }
        List<Tagged> taggeds = taggedRepository.mFindAllByPinAndUser(pin.getId(), socialId);
        if(taggeds.isEmpty()) {
            return;
        }
        for (Tagged tagged: taggeds) {
            Tag tag = tagRepository.findById(tagged.getTag().getId()).orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.TAG_NOT_FOUND));
            int count = tagged.getCount() - 1;
            if(count == 0) {
                taggedRepository.delete(tagged);
                em.flush();
            }
            else {
                System.out.println("0이 아닙니다.");
                tagged.changeTagged(pin, tag, count);
                em.flush();
            }
        }
    }
}
