package com.brightkut.hotelservice.mapper;

import com.brightkut.hotelservice.dto.CreateHotelDto;
import com.brightkut.hotelservice.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    @Mapping(target = "hotelName", source = "createHotelDto.hotelName")
    @Mapping(target = "hotelDesc", source = "createHotelDto.hotelDesc")
    @Mapping(target = "location", source = "createHotelDto.location")
    @Mapping(target = "checkedInTime", source = "createHotelDto.checkedInTime")
    @Mapping(target = "checkedOutTime", source = "createHotelDto.checkedOutTime")
    Hotel toHotel(CreateHotelDto createHotelDto);
}
