package com.brightkut.userservice.entity;

import com.brightkut.kei.db.BaseEntity;
import com.brightkut.kei.uuid.UuidV7Id;
import com.brightkut.userservice.enums.RoleEnum;
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

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserRole extends BaseEntity {
    @Id
    @UuidV7Id
    private UUID userRoleId;
    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName = RoleEnum.NORMAL_USER;
}
