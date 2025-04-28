package com.brightkut.hotelservice.entity;

import java.util.UUID;

import com.brightkut.kei.db.BaseEntity;
import com.brightkut.kei.uuid.UuidV7Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class Room extends BaseEntity {
    @Id
    @UuidV7Id
    private UUID roomId;
    @Column(nullable = false)
    private Long roomNo;
    @Column(nullable = false)
    private Integer floor;
    @Column(nullable = false)
    private Long roomPrice;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
}
