package com.book_everywhere.web.exception.customs;

public class SQLException extends CustomException{
    public SQLException(CustomErrorCode errorCode) {
        super(errorCode);
    }
}
