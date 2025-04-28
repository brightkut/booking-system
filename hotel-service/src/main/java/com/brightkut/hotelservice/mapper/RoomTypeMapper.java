package com.brightkut.hotelservice.mapper;

import com.brightkut.hotelservice.dto.CreateRoomTypeDto;
import com.brightkut.hotelservice.entity.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper {

    @Mapping(target = "roomType", source = "createRoomTypeDto.roomType")
    @Mapping(target = "roomSize", source = "createRoomTypeDto.roomSize")
    @Mapping(target = "numberOfBedroom", source = "createRoomTypeDto.numberOfBedroom")
    @Mapping(target = "numberOfBathroom", source = "createRoomTypeDto.numberOfBathroom")
    @Mapping(target = "hasAirConditioner", source = "createRoomTypeDto.hasAirConditioner")
    @Mapping(target = "hasRefrigerator", source = "createRoomTypeDto.hasRefrigerator")
    RoomType toRoomType(CreateRoomTypeDto createRoomTypeDto);
}
