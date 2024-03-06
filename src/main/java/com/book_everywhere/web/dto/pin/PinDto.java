package com.book_everywhere.web.dto.pin;

import java.sql.Timestamp;
import com.book_everywhere.domain.pin.Pin;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PinDto {
    private Long id;
    private double placeId;
    private double latitude;
    private double longitude;
    //개인설정이름
    private String title;
    private String address;
    private String url;
    private Timestamp createAt;

    public Pin toEntity(){
        return Pin.builder()
                .title(title)
                .latitude(latitude)
                .address(address)
                .longitude(longitude)
                .build();
    }
}
