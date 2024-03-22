package com.book_everywhere.pin.service;

import com.book_everywhere.pin.dto.PinDto;
import com.book_everywhere.pin.dto.PinWithTagCountRespDto;
import com.book_everywhere.review.dto.ReviewRespDto;

import java.util.List;

public interface PinService {
    List<PinDto> 전체지도조회();

    void 핀생성(ReviewRespDto reviewRespDto);

    List<PinWithTagCountRespDto> 핀의상위5개태그개수와함께조회();

    List<PinDto> 태그조회(String content);

    List<PinWithTagCountRespDto> 공유또는개인핀의상위5개태그개수와함께조회(boolean isPrivate);

    List<PinDto> 나만의지도조회(Long socialId);
}
