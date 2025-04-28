package com.brightkut.hotelservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddRoomDto {
    private UUID hotelId;
    private UUID roomTypeId;
    @NotNull
    private Long roomNo;
    @NotNull
    private Integer floor;
    @NotNull
    private Long roomPrice;
}
