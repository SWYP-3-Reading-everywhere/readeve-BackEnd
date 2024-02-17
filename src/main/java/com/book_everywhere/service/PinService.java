package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
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

    @Transactional(readOnly = true)
    public List<Pin> 전체핀조회() {
        return pinRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pin 단일핀조회(int pinId) {
        return pinRepository.findById(pinId).orElseThrow();
    }


    @Transactional()
    public void 핀생성(PinDto pinDto ,@AuthenticationPrincipal OAuth2User oAuth2User){
        Pin pin = pinDto.toEntity();
        pinRepository.save(pin);
    }
}
