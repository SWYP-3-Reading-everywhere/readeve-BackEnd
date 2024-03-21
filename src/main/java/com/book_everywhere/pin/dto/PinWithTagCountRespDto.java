package com.book_everywhere.pin.dto;

import com.book_everywhere.tag.dto.TagCountRespDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
public class PinWithTagCountRespDto {
    private Long id;
    private double placeId;
    private double latitude;
    private double longitude;
    //개인설정이름
    private String title;
    private String address;
    private String url;
    private Timestamp createAt;
    private List<TagCountRespDto> tagCountRespDtos;
}
