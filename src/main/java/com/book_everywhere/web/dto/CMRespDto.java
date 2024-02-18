package com.book_everywhere.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CMRespDto<T> {
    private HttpStatus statusCode;
    private T data;
    private String message;

}