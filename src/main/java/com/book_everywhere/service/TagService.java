package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.tag.Tag;
import com.book_everywhere.domain.tag.TagRepository;
import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.domain.tagged.TaggedRepository;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.visit.Visit;
import com.book_everywhere.web.dto.review.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TaggedRepository taggedRepository;
    private final PinRepository pinRepository;

    @Transactional
    public void 태그등록(String tagContent, Long pinId) {
        Tag tag = tagRepository.mFindTagByContent(tagContent);
        Pin pin = pinRepository.mFindByPinId(pinId);

        Tagged tagged = taggedRepository.mFindTagged(tag.getId(), pin.getId());

        if (tagged != null) {
            Tagged newTagged = Tagged.builder()
                    .pin(pin)
                    .tag(tag)
                    .build();

            taggedRepository.save(newTagged);
        }

    }

}
