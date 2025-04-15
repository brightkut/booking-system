package com.brightkut.userservice.repository;

import com.brightkut.userservice.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserAuthRepository extends JpaRepository<UserAuth, UUID> {

    UserAuth findByEmail(@Param("email") String email);
}
