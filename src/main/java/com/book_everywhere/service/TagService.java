package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.tag.Tag;
import com.book_everywhere.domain.tag.TagRepository;
import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.domain.tagged.TaggedRepository;
import com.book_everywhere.web.dto.review.ReviewRespDto;
import com.book_everywhere.web.dto.tag.TagRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TaggedRepository taggedRepository;
    private final PinRepository pinRepository;

    @Transactional
    public void 태그등록(ReviewRespDto reviewRespDto) {
        List<TagRespDto> tags = reviewRespDto.getTags();

        for (TagRespDto tagRespDto : tags) {
            Tag tag = tagRepository.mFindTagByName(tagRespDto.getContent());
            Pin pin = pinRepository.mFindPinByAddress(reviewRespDto.getPinRespDto().getAddress());

            Tagged tagged = taggedRepository.mFindTagged(tag.getId(), pin.getId());

            if (tagged == null && tagRespDto.isSelected()) {

                Tagged newTagged = Tagged.builder()
                        .pin(pin)
                        .tag(tag)
                        .build();

                taggedRepository.save(newTagged);
            }
        }
    }

    public List<String> 모든태그조회() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(Tag::getContent).toList();
    }

}
