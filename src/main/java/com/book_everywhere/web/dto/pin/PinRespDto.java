package com.book_everywhere.web.dto.pin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PinRespDto {
    private String name;
    private double y;
    private double x;
    private String address;
    private boolean isPrivate;
}
