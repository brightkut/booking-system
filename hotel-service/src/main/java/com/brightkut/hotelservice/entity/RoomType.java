package com.brightkut.hotelservice.entity;

import java.util.UUID;

import com.brightkut.hotelservice.RoomTypeEnum;
import com.brightkut.kei.db.BaseEntity;
import com.brightkut.kei.uuid.UuidV7Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
@Entity
public class RoomType extends BaseEntity {
    @Id
    @UuidV7Id
    private UUID roomTypeId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomTypeEnum roomType;
    @Column(nullable = false)
    private String roomSize;
    @Column(nullable = false)
    private Integer numberOfBedroom;
    @Column(nullable = false)
    private Integer numberOfBathroom;
    @Column(nullable = false)
    private Boolean hasAirConditioner;
    @Column(nullable = false)
    private Boolean hasRefrigerator;
}
