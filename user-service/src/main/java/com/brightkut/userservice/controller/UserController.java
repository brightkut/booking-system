package com.brightkut.userservice.controller;

import com.brightkut.commonlib.lib.api.ApiRes;
import com.brightkut.userservice.dto.AccessTokenDto;
import com.brightkut.userservice.dto.CreateUserRoleDto;
import com.brightkut.userservice.dto.LoginDto;
import com.brightkut.userservice.dto.RegisterUserDto;
import com.brightkut.userservice.dto.VerifyEmailDto;
import com.brightkut.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public ApiRes<AccessTokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        var accessToken = userService.login(loginDto);

        return ApiRes.success(HttpStatus.OK, accessToken);
    }

    @PostMapping("/logout")
    public ApiRes<String> logout(@Valid @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken) {
        var authToken = accessToken.substring(7);

        userService.logout(authToken);

        return ApiRes.success(HttpStatus.OK, "Logout successfully");
    }

    @PostMapping("/roles")
    public ApiRes<String> createUserRole(@Valid @RequestBody CreateUserRoleDto createUserRoleDto) {
        userService.createUserRole(createUserRoleDto);

        return ApiRes.success(HttpStatus.CREATED, "Create user role successfully");
    }

    @PostMapping("/verify-email")
    public ApiRes<String> verifyEmail(@Valid @RequestBody VerifyEmailDto verifyEmailDto) {
        userService.verifyEmail(verifyEmailDto);

        return ApiRes.success(HttpStatus.OK, "Verify email successfully");
    }
}
