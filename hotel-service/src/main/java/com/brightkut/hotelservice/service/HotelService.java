package com.brightkut.hotelservice.service;

import com.brightkut.hotelservice.dto.AddRoomDto;
import com.brightkut.hotelservice.dto.CreateHotelDto;
import com.brightkut.hotelservice.dto.CreateRoomTypeDto;
import com.brightkut.hotelservice.entity.RoomType;
import com.brightkut.hotelservice.mapper.HotelMapper;
import com.brightkut.hotelservice.mapper.RoomMapper;
import com.brightkut.hotelservice.mapper.RoomTypeMapper;
import com.brightkut.hotelservice.repository.HotelRepository;
import com.brightkut.hotelservice.repository.RoomTypeRepository;
import com.brightkut.kei.exception.AlreadyExistException;
import com.brightkut.kei.exception.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final HotelMapper hotelMapper;
    private final RoomTypeMapper roomTypeMapper;
    private final RoomMapper roomMapper;

    public HotelService(HotelRepository hotelRepository, RoomTypeRepository roomTypeRepository, HotelMapper hotelMapper, RoomTypeMapper roomTypeMapper, RoomMapper roomMapper) {
        this.hotelRepository = hotelRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.hotelMapper = hotelMapper;
        this.roomTypeMapper = roomTypeMapper;
        this.roomMapper = roomMapper;
    }

    public void createHotel(CreateHotelDto createHotelDto) {
        var hotel = hotelRepository.findByHotelName(createHotelDto.getHotelName());

        if(hotel != null) {
            throw new AlreadyExistException("Hotel already exists");
        }

        hotel = hotelMapper.toHotel(createHotelDto);

        hotelRepository.save(hotel);
    }

    public void createRoomType(CreateRoomTypeDto createRoomTypeDto) {
        var roomType = roomTypeRepository.findByRoomType(createRoomTypeDto.getRoomType());

        if(roomType != null) {
            throw new AlreadyExistException("Room type already exists");
        }

        roomType = roomTypeMapper.toRoomType(createRoomTypeDto);

        roomTypeRepository.save(roomType);
    }

    public List<RoomType> getRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public void addRoom(@Valid AddRoomDto addRoomDto) {
        var hotel = hotelRepository.findById(addRoomDto.getHotelId())
                .orElseThrow(() -> new NotFoundException("Hotel not found"));

        var roomType = roomTypeRepository.findById(addRoomDto.getRoomTypeId())
                .orElseThrow(() -> new NotFoundException("Room type not found"));

        var room = roomMapper.toRoom(addRoomDto);
        room.setHotel(hotel);
        room.setRoomType(roomType);
        hotel.getRooms().add(room);

        hotelRepository.save(hotel);
    }
}
