package com.book_everywhere.pin.controller;

import com.book_everywhere.pin.service.PinService;
import com.book_everywhere.review.service.ReviewService;
import com.book_everywhere.common.dto.CMRespDto;
import com.book_everywhere.pin.dto.PinDto;
import com.book_everywhere.pin.dto.PinWithTagCountRespDto;
import com.book_everywhere.review.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class PinController {

    private final PinService pinService;
    private final ReviewService reviewService;


    @GetMapping("/api/map")
    public CMRespDto<?> allPin() {
        List<PinDto> result = pinService.전체지도조회();
        return new CMRespDto<>(HttpStatus.OK, result,"전체 지도 조회 성공!");
    }

    @GetMapping("/api/map/tag/count")
    public CMRespDto<?> allPinWithTagCount() {
        List<PinWithTagCountRespDto> result = pinService.핀의상위5개태그개수와함께조회();
        return new CMRespDto<>(HttpStatus.OK, result, "전체 지도 상위 5개 태그와 함께 조회 성공");
    }
    @GetMapping("/api/map/tag/count/{isPrivate}")
    public CMRespDto<?> allPublicPinWithTagCount(@PathVariable boolean isPrivate) {
        List<PinWithTagCountRespDto> result = pinService.공유또는개인핀의상위5개태그개수와함께조회(isPrivate);
        return new CMRespDto<>(HttpStatus.OK, result, "공유/개인 지도 상위 5개 태그와 함께 조회 성공");
    }

    //핀을 눌렀을때 핀에 해당하는 독후감 정보 조회
    @GetMapping("/api/pin/{pinId}")
    public CMRespDto<?> pinDetails(@PathVariable Long pinId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        List<ReviewDto> result = reviewService.단일핀독후감조회((Long) oAuth2User.getAttributes().get("id"),pinId);
        return new CMRespDto<>(HttpStatus.OK, result,"단일 핀 종속 독후감 조회 성공!");
    }

    //나만의 지도 기능
    @GetMapping("/api/map/{socialId}")
    public CMRespDto<?> userMap(@PathVariable Long socialId) {
        List<PinDto> result = pinService.나만의지도조회(socialId);
        return new CMRespDto<>(HttpStatus.OK, result,"유저 독후감 조회 성공!");
    }

    //태그String에 대한 pin 추출
    @GetMapping("/api/pin/tag/{content}")
    public CMRespDto<?> taggedPin(@PathVariable String content) {
        List<PinDto> result = pinService.태그조회(content);
        return new CMRespDto<>(HttpStatus.OK, result,"태그 조회 성공!"); // 이 부분 논의
    }

    @GetMapping("/api/mypage/review")
    public CMRespDto<?> userReview(@RequestParam Long socialId) {
        List<ReviewDto> result = reviewService.유저모든독후감조회(socialId);
        return new CMRespDto<>(HttpStatus.OK, result,"모든 독후감 조회 성공!");
    }

}
