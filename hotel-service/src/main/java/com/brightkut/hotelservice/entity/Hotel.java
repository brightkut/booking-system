package com.brightkut.hotelservice.entity;

import com.brightkut.kei.db.BaseEntity;
import com.brightkut.kei.uuid.UuidV7Id;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Hotel extends BaseEntity {
    @Id
    @UuidV7Id
    private UUID hotelId;
    @Column(length = 20, nullable = false)
    private String hotelName;
    @Column(length = 30, nullable = false)
    private String hotelDesc;
    @Column(length = 10, nullable = false)
    private String location;
    @Builder.Default
    @Column(nullable = false)
    private Boolean isActive = false;
    @Column(nullable = false)
    private String checkedInTime;
    @Column(nullable = false)
    private String checkedOutTime;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Room> rooms;

}
