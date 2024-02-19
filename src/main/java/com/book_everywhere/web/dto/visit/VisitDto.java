package com.book_everywhere.web.dto.visit;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.visit.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitDto {
    // 등록 Dto입니다 Front -> Back
    private Long socialId;
    private Long pinId;
    private boolean isPrivate;

}
