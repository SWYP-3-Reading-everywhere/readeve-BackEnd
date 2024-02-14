package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.web.dto.pin.PinDto;
import lombok.RequiredArgsConstructor;
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

    //    <-- 유저 기능이 완료된다면 수정할 것 -->>
    @Transactional()
    public void 핀생성(PinDto pinDto) {//,@AuthenticationPrincipal PrincipalDetails principalDetails){
//        Pin pin = pinDto.toEntity(principalDetails.getUser().getId());
//        pinRepository.save();
    }
}
