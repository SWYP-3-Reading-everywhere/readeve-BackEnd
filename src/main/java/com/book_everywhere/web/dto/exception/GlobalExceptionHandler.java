package com.book_everywhere.web.dto.exception;


import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.exception.customs.CustomErrorCode;
import com.book_everywhere.web.dto.exception.customs.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public CMRespDto<Object> handleCustomException(CustomException ex) {
        CustomErrorCode errorCode = ex.getErrorCode();
        return new CMRespDto<>(errorCode.getCode(), null, errorCode.getMessage());
    }
}