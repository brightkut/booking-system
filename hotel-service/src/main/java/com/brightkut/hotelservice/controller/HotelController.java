package com.brightkut.hotelservice.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brightkut.hotelservice.client.UserClient;
import com.brightkut.kei.api.ApiRes;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private final UserClient userClient;

    public HotelController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    public ApiRes<String> createHotel(@Valid @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken) {

        userClient.verifyAccessToken(accessToken);

        return ApiRes.success(HttpStatus.OK, "create hotel successfully");
    }
}
