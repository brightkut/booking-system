package com.brightkut.hotelservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CreateHotelDto {
    @NotBlank
    @Size(max = 20)
    private String hotelName;
    @NotBlank
    @Size(max = 30)
    private String hotelDesc;
    @NotBlank
    @Size(max = 10)
    private String location;
    private String checkedInTime;
    private String checkedOutTime;
}
