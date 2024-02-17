package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.visit.VisitRepository;
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
    private final VisitRepository visitRepository;

    @Transactional(readOnly = true)
    public List<Pin> 전체지도조회() {
        return pinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pin 단일핀조회(int pinId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        // 단일 핀을 누른다 그럼 거기에 방문한 사람의 book정보를 줘야함
        return null;
    }


    @Transactional
    public void 핀생성(PinDto pinDto ,@AuthenticationPrincipal OAuth2User oAuth2User){
        Pin pin = pinDto.toEntity();
        pinRepository.save(pin);
    }

    @Transactional(readOnly = true)
    public void 나만의지도조회(@AuthenticationPrincipal OAuth2User oAuth2User){
//        visitRepository.findById()
    }
}
