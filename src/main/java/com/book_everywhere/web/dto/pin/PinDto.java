package com.book_everywhere.web.dto.pin;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.user.User;
import lombok.Data;

@Data
public class PinDto {
    private String title;
    private double latitude;
    private double longitude;
    private String address;

    public Pin toEntity(){
        return Pin.builder()
                .title(title)
                .latitude(latitude)
                .address(address)
                .longitude(longitude)
                .build();
    }
}
