package com.brightkut.userservice.controller;

import com.brightkut.userservice.entity.UserAuth;
import com.brightkut.userservice.repository.UserAuthRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserAuthRepository userAuthRepository;

    public UserController(UserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @GetMapping
    public UserAuth getUser() {
        return userAuthRepository.findAll().get(0);
    }
}
