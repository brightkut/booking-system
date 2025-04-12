package com.brightkut.userservice.entity;

import com.brightkut.commonlib.lib.db.BaseEntity;
import com.brightkut.commonlib.lib.uuid.UuidV7Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class UserAuth extends BaseEntity {
    @Id
    @UuidV7Id
    private UUID userAuthId;

    @Column
    private String name;
}
