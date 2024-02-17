package com.book_everywhere.web.dto.visit;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.visit.Visit;
import lombok.Data;

@Data
public class VisitDto {
    private boolean isPrivate;

    public Visit toEntity(User user, Pin pin) {
        return Visit.builder()
                .user(user)
                .pin(pin)
                .isPrivate(isPrivate)
                .build();
    }
}
