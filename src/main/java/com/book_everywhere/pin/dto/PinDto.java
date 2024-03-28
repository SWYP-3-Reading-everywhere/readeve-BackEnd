package com.book_everywhere.pin.dto;

import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class PinDto {
    private Long id;
    private double placeId;
    private double y;
    private double x;
    //개인설정이름
    private String title;
    private String address;
    private String url;
    private LocalDateTime createDate;

    public Pin toEntity() {
        return Pin.builder()
                .title(title)
                .latitude(y)
                .address(address)
                .longitude(x)
                .build();
    }

    public static PinDto toDto(Pin pin) {
        return new PinDto(pin.getId(),
                pin.getPlaceId(),
                pin.getLatitude(),
                pin.getLongitude(),
                pin.getTitle(),
                pin.getAddress(),
                pin.getUrl(),
                pin.getCreatedDate());
    }
}
