package com.book_everywhere.web.dto.exception;


import com.book_everywhere.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    public CMRespDto<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return new CMRespDto<>(HttpStatus.NOT_FOUND, null, ex.getMessage());
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseBody
    public CMRespDto<Object> handleBookNotFoundException(BookNotFoundException ex) {
        return new CMRespDto<>(HttpStatus.NOT_FOUND, null, ex.getMessage());
    }
}