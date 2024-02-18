package com.book_everywhere.web;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.service.PinService;
import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.pin.PinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PinController {

    private final PinService pinService;

    @GetMapping("/pin")
    public CMRespDto<?> allPin() {
        List<PinDto> result = pinService.전체지도조회();
        return new CMRespDto<>(HttpStatus.OK, result,"전체 지도 조회 성공!");
    }

    //핀을 눌렀을때 핀에 해당하는 독후감 정보 조회
    @GetMapping("/pin/{id}")
    public CMRespDto<?> pinDetails(@PathVariable Long id, @AuthenticationPrincipal OAuth2User oAuth2User) {
        List<Review> result = pinService.단일핀조회(id, oAuth2User);
        return new CMRespDto<>(HttpStatus.OK, result,"핀 조회 성공!");
    }
    //태그String에 대한 pin 추출
    @GetMapping("/pin/tag/{tagContent}")
    public CMRespDto<?> taggedPin(@PathVariable String tagContent) {
        List<Pin> result = pinService.태그조회(tagContent);
        return new CMRespDto<>(HttpStatus.OK, result,"태그 조회 성공!");
    }

    @PostMapping("/pin")
    public CMRespDto<?> createPin(PinDto pinDto) {
        pinService.핀생성(pinDto);
        return new CMRespDto<>(HttpStatus.OK,null ,"핀생성완료!");
    }

}
