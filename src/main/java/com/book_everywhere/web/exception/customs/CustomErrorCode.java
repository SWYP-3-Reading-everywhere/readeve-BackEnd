package com.book_everywhere.web.exception.customs;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER NOT FOUND"),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK NOT FOUND"),
    PIN_NOT_FOUND(HttpStatus.NOT_FOUND, "PIN NOT FOUND"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW NOT FOUND");


    private HttpStatus code;
    private String message;

    CustomErrorCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
