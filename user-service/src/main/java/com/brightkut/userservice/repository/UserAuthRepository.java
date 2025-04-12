package com.brightkut.userservice.repository;

import com.brightkut.userservice.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAuthRepository extends JpaRepository<UserAuth, UUID> {
}
