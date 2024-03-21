package com.book_everywhere.web.exception.customs;

public class PropertyBadRequestException extends CustomException{
    public PropertyBadRequestException(CustomErrorCode errorCode) {
        super(errorCode);
    }
}
