package com.book_everywhere.web.exception.customs;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER NOT FOUND"),
    BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOK NOT FOUND"),
    PIN_NOT_FOUND(HttpStatus.NOT_FOUND, "PIN NOT FOUND"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW NOT FOUND"),
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "TAG NOT FOUND"),
    ADDRESS_IS_NOT_NULL(HttpStatus.BAD_REQUEST, "주소는 공백이 될 수 없습니다."),
    BOOK_IS_NOT_NULL(HttpStatus.BAD_REQUEST, "책 정보는 공백이 될 수 없습니다."),
    TITLE_IS_NOT_BLANK(HttpStatus.BAD_REQUEST, "제목은 공백이 될 수 없습니다."),
    CONTENT_IS_NOT_BLANK(HttpStatus.BAD_REQUEST, "내용은 공백이 될 수 없습니다."),
    TITLE_IS_NOT_OVER_20(HttpStatus.BAD_REQUEST, "제목은 20자 이상이 될 수 없습니다."),
    CONTENT_IS_NOT_OVER_1500(HttpStatus.BAD_REQUEST, "내용은 1500자 이상이 될 수 없습니다."),
    PIN_HAS_TAGS(HttpStatus.INTERNAL_SERVER_ERROR, "핀에 태그가 있어 삭제 할 수 없습니다.");


    private HttpStatus code;
    private String message;

    CustomErrorCode(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }
}
