package com.brightkut.userservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.brightkut.userservice.entity.UserRole;
import com.brightkut.userservice.enums.RoleEnum;
import org.springframework.data.repository.query.Param;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    @Query("SELECT u FROM UserRole u where u.roleName = :roleName")
    UserRole findByRole(@Param("roleName") RoleEnum roleName);
}
