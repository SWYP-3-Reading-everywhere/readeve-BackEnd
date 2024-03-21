package com.book_everywhere.exception.customs;

public class EntityNotFoundException extends CustomException {
    public EntityNotFoundException(CustomErrorCode errorCode) {
        super(errorCode);
    }
}
