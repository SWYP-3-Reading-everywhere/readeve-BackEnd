package com.book_everywhere.web.dto.pin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PinRespDto {
    private String name;
    private double placeId;
    private double y;
    private double x;
    private String address;
    private boolean isPrivate;
    //3월 2일 추가 공유지도
    private String url;
}
