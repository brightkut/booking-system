package com.brightkut.userservice.controller;

import com.brightkut.userservice.entity.UserAuth;
import com.brightkut.userservice.repository.UserAuthRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserAuthRepository userAuthRepository;
    private final RedisCacheManager redisCacheManager;

    public UserController(UserAuthRepository userAuthRepository, RedisCacheManager redisCacheManager) {
        this.userAuthRepository = userAuthRepository;
        this.redisCacheManager = redisCacheManager;
    }

    @GetMapping
    @Cacheable( value = "userToken", key = "'fix_key'")
    public String getUser() {
        return "test cache";
    }
}
