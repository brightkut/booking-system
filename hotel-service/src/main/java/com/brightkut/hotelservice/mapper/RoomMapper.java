package com.brightkut.hotelservice.mapper;


import com.brightkut.hotelservice.dto.AddRoomDto;
import com.brightkut.hotelservice.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "roomNo", source = "addRoomDto.roomNo")
    @Mapping(target = "floor", source = "addRoomDto.floor")
    @Mapping(target = "roomPrice", source = "addRoomDto.roomPrice")
    Room toRoom(AddRoomDto addRoomDto);
}
