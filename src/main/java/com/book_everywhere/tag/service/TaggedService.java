package com.book_everywhere.tag.service;

import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.pin.repository.PinRepository;
import com.book_everywhere.tag.entity.Tag;
import com.book_everywhere.tag.repository.TagRepository;
import com.book_everywhere.tag.entity.Tagged;
import com.book_everywhere.tag.repository.TaggedRepository;
import com.book_everywhere.auth.entity.User;
import com.book_everywhere.auth.repository.UserRepository;
import com.book_everywhere.review.dto.ReviewRespDto;
import com.book_everywhere.tag.dto.TagDto;
import com.book_everywhere.exception.customs.CustomErrorCode;
import com.book_everywhere.exception.customs.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaggedService {
    private final TagRepository tagRepository;
    private final TaggedRepository taggedRepository;
    private final PinRepository pinRepository;
    private final UserRepository userRepository;

    @Transactional
    public void 태그등록(ReviewRespDto reviewRespDto) {
        List<String> tags = reviewRespDto.getTags();
        User user = userRepository.findBySocialId(reviewRespDto.getSocialId()).orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.USER_NOT_FOUND));

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
                        .user(user)
                        .build();
                taggedRepository.save(newTagged);
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

    @Transactional
    public void 태그삭제(String address, Long socialId) {
        Pin pin = pinRepository.mFindPinByAddress(address);
        if(pin == null) {
            throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
        }
        taggedRepository.deleteAllByPinAndUser(pin.getId(), socialId);
    }
}
