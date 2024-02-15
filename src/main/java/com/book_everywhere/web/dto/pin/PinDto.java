package com.book_everywhere.web.dto.pin;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.user.User;
import lombok.Data;

@Data
public class PinDto {
    private String title;
    private double latitude;
    private double longitude;
    private String memo;
    private boolean isPrivate;
    private String message;

    public Pin toEntity(User userEntity){
        return Pin.builder()
                .title(title)
                .latitude(latitude)
                .longitude(longitude)
                .memo(memo)
                .user(userEntity)
                .build();
    }
}
