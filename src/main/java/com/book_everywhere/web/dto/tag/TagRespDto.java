package com.book_everywhere.web.dto.tag;

import com.book_everywhere.domain.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TagRespDto {
    private String content;
    private boolean isSelected; // tagged 컬럼에 사용됨
}
