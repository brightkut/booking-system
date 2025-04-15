package com.brightkut.userservice.controller;

import com.brightkut.commonlib.lib.api.ApiRes;
import com.brightkut.userservice.dto.CreateUserRoleDto;
import com.brightkut.userservice.dto.RegisterUserDto;
import com.brightkut.userservice.repository.UserAuthRepository;
import com.brightkut.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiRes<String> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        userService.registerUser(registerUserDto);

        return ApiRes.success(HttpStatus.CREATED, "Registered user successfully");
    }

    @PostMapping("/roles")
    public ApiRes<String> createUserRole(@Valid @RequestBody CreateUserRoleDto createUserRoleDto) {
        userService.createUserRole(createUserRoleDto);

        return ApiRes.success(HttpStatus.CREATED, "Create user role successfully");
    }
}
