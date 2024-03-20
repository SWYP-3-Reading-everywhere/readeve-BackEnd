package com.book_everywhere.web.exception.customs;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {
    private final CustomErrorCode errorCode;

    public CustomException(CustomErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}