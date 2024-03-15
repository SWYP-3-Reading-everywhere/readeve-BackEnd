package com.book_everywhere.web.exception;


import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.exception.customs.CustomErrorCode;
import com.book_everywhere.web.exception.customs.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {
        CustomErrorCode errorCode = ex.getErrorCode();
        CMRespDto<?> response = new CMRespDto<>(errorCode.getCode(), null, errorCode.getMessage());
        return new ResponseEntity<>(response, errorCode.getCode());
    }
}