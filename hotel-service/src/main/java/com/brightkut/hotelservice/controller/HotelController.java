package com.brightkut.hotelservice.controller;

import com.brightkut.hotelservice.dto.AddRoomDto;
import com.brightkut.hotelservice.dto.CreateHotelDto;
import com.brightkut.hotelservice.dto.CreateRoomTypeDto;
import com.brightkut.hotelservice.entity.RoomType;
import com.brightkut.hotelservice.service.HotelService;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brightkut.hotelservice.client.UserClient;
import com.brightkut.kei.api.ApiRes;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final UserClient userClient;
    private final HotelService hotelService;

    public HotelController(UserClient userClient, HotelService hotelService) {
        this.userClient = userClient;
        this.hotelService = hotelService;
    }

    @PostMapping
    public ApiRes<String> createHotel(@Valid @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken, @RequestBody CreateHotelDto createHotelDto) {

        userClient.verifyAccessToken(accessToken);

        hotelService.createHotel(createHotelDto);

        return ApiRes.success(HttpStatus.CREATED, "create hotel successfully");
    }

    @PostMapping("/room-types")
    public ApiRes<String> createRoomType(@RequestBody CreateRoomTypeDto createRoomTypeDto) {
        hotelService.createRoomType(createRoomTypeDto);
        return ApiRes.success(HttpStatus.CREATED, "create room type successfully");
    }

    @GetMapping("/room-types")
    public ApiRes<List<RoomType>> getRoomTypes() {
        return ApiRes.success(HttpStatus.OK, hotelService.getRoomTypes());
    }

    @PostMapping
    public ApiRes<String> addRoom(@Valid @RequestBody AddRoomDto addRoomDto) {
        hotelService.addRoom(addRoomDto);
        return ApiRes.success(HttpStatus.CREATED, "add room successfully");
    }
}
