package com.brightkut.commonlib.lib.db;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder // use this instead of @Builder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass // add this to make this is a superclass
@EntityListeners(AuditingEntityListener.class)// Add this to allow using @CreatedDate and @LastModifiedDate
public abstract class BaseEntity {
    @Column(nullable = false)
    private Boolean isDeleted = false;

    @CreatedDate
    @Column(insertable = true, updatable = false)// set this to add this field only when the first time we insert this data
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false, updatable = true)// set this to make sure the first time we insert the data the lastModifiedDate will be null
    private LocalDateTime updatedAt;
}
