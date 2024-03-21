package com.book_everywhere.exception.customs;

public class PropertyBadRequestException extends CustomException{
    public PropertyBadRequestException(CustomErrorCode errorCode) {
        super(errorCode);
    }
}
