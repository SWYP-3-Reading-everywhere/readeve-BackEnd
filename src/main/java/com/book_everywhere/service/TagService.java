package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.tag.Tag;
import com.book_everywhere.domain.tag.TagRepository;
import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.domain.tagged.TaggedRepository;
import com.book_everywhere.web.dto.review.ReviewRespDto;
import com.book_everywhere.web.dto.tag.TagDto;
import com.book_everywhere.web.dto.tag.TaggedDto;
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

    @Transactional
    public void 태그등록또는수정(ReviewRespDto reviewRespDto) {
        List<String> tags = reviewRespDto.getTags();

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

}
