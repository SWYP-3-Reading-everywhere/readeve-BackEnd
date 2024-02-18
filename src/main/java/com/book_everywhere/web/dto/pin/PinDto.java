package com.book_everywhere.web.dto.pin;

import java.sql.Timestamp;
import com.book_everywhere.domain.pin.Pin;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PinDto {
    private Long id;
    private double latitude;
    private double longitude;
    private String title;
    private String address;
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
