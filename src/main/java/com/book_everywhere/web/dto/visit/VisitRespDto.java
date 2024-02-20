package com.book_everywhere.web.dto.visit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitRespDto {
    // 등록 Dto입니다 Front -> Back
    private Long socialId;
    private Long pinId;
    private boolean isPrivate;

}
