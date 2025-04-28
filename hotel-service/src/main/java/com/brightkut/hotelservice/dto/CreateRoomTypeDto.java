package com.brightkut.hotelservice.dto;

import com.brightkut.hotelservice.RoomTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRoomTypeDto {
    private RoomTypeEnum roomType;
    @NotBlank
    private String roomSize;
    @NotNull
    private Integer numberOfBedroom;
    @NotNull
    private Integer numberOfBathroom;
    @NotNull
    private Boolean hasAirConditioner;
    @NotNull
    private Boolean hasRefrigerator;
}
