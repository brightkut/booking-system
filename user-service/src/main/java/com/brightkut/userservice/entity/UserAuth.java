package com.brightkut.userservice.entity;

import com.brightkut.commonlib.lib.db.BaseEntity;
import com.brightkut.commonlib.lib.uuid.UuidV7Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class UserAuth extends BaseEntity {
    @Id
    @UuidV7Id
    private UUID userAuthId;
    @Column(length = 30, nullable = false, unique = true)
    private String email;
    @Column(length = 64, nullable = false)
    private String passwordHash;
    private Boolean isVerified = false;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRole userRole;
    @OneToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;
    @OneToMany(mappedBy = "userAuth")
    private List<UserPaymentCard> userPaymentCards;
}
