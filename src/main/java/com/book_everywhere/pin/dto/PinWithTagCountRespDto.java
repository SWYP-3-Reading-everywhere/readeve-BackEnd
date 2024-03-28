package com.book_everywhere.pin.dto;

import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.tag.dto.TagCountRespDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PinWithTagCountRespDto {
    private Long id;
    private double placeId;
    private double y;
    private double x;
    //개인설정이름
    private String title;
    private String address;
    private String url;
    private LocalDateTime createDate;
    private List<TagCountRespDto> tagCountRespDtos;

    public static PinWithTagCountRespDto toDto(Pin pin, List<TagCountRespDto> tagCountRespDtos) {
        return new PinWithTagCountRespDto(
                pin.getId(),
                pin.getPlaceId(),
                pin.getLatitude(),
                pin.getLongitude(),
                pin.getTitle(),
                pin.getAddress(),
                pin.getUrl(),
                pin.getCreatedDate(),
                tagCountRespDtos
        );
    }
}
