package com.book_everywhere.web.dto.exception.customs;

public class EntityNotFoundException extends CustomException {
    public EntityNotFoundException(CustomErrorCode errorCode) {
        super(errorCode);
    }
}
