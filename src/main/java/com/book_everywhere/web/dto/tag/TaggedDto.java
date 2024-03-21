package com.book_everywhere.web.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaggedDto {
    private Long pinId;
    private String tagName;
    private int count;
    private Timestamp createAt;
}
