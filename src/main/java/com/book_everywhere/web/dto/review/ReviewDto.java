package com.book_everywhere.web.dto.review;

import lombok.Data;


@Data
public class ReviewDto {
    private String title;
    private String content;
    private boolean isPrivate;

    public boolean getIsPrivate() {
        return isPrivate;
    }


}
