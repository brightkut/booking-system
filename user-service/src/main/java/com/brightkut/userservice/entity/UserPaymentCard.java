package com.brightkut.userservice.entity;

import com.brightkut.kei.db.BaseEntity;
import com.brightkut.kei.uuid.UuidV7Id;
import com.brightkut.userservice.enums.CardTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class UserPaymentCard extends BaseEntity {
    @Id
    @UuidV7Id
    private UUID userPaymentCardId;
    @Column(length = 20, nullable = false)
    private String cardHolderName;
    @Column(length = 16, nullable = false)
    private String cardNumber;
    @Enumerated(EnumType.STRING)
    private CardTypeEnum cardType;


    @ManyToOne
    @JoinColumn(name = "user_auth_id")
    private UserAuth userAuth;
}
