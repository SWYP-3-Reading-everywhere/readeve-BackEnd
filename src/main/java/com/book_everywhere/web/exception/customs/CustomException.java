package com.book_everywhere.web.exception.customs;

public abstract class CustomException extends RuntimeException {
    private final CustomErrorCode errorCode;

    public CustomException(CustomErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomErrorCode getErrorCode() {
        return errorCode;
    }
}