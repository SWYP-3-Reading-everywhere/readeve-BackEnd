package com.book_everywhere.web.dto.pin;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.user.User;
import lombok.Data;

@Data
public class PinDto {
    private String pinTitle;
    private double latitude;
    private double longitude;
    private String description;
    private boolean isPrivate;
    private String message;

    public Pin toEntity(User userEntity){
        return Pin.builder()
                .pinTitle(pinTitle)
                .latitude(latitude)
                .longitude(longitude)
                .description(description)
                .isPrivate(isPrivate)
                .message(message)
                .user(userEntity)
                .build();
    }
}
