package com.brightkut.hotelservice.repository;

import com.brightkut.hotelservice.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {
    Hotel findByHotelName(String hotelName);
}
