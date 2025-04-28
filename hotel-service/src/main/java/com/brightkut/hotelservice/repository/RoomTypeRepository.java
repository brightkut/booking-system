package com.brightkut.hotelservice.repository;

import com.brightkut.hotelservice.RoomTypeEnum;
import com.brightkut.hotelservice.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {

    @Query("SELECT r FROM RoomType r where r.roomType = :roomType")
    RoomType findByRoomType(@Param("roomType") RoomTypeEnum roomType);
}
