package com.brightkut.userservice.service;

import com.brightkut.commonlib.lib.exception.AlreadyExistException;
import com.brightkut.userservice.dto.CreateUserRoleDto;
import com.brightkut.userservice.dto.RegisterUserDto;
import com.brightkut.userservice.entity.UserRole;
import com.brightkut.userservice.enums.RoleEnum;
import com.brightkut.userservice.mapper.UserMapper;
import com.brightkut.userservice.repository.UserAuthRepository;
import com.brightkut.userservice.repository.UserRoleRepository;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserAuthRepository userAuthRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;

    public UserService(UserAuthRepository userAuthRepository, UserRoleRepository userRoleRepository,
            UserMapper userMapper) {
        this.userAuthRepository = userAuthRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
    }

    public void registerUser(RegisterUserDto registerUserDto) {
        validateEmailExists(registerUserDto.getEmail());

        var userRole = userRoleRepository.findByRole(RoleEnum.NORMAL_USER);

        var userAuth = userMapper.toUserAuth(registerUserDto, userRole);

        userAuthRepository.save(userAuth);
    }

    public void createUserRole(CreateUserRoleDto createUserRoleDto) {
        var userRole = UserRole
                .builder()
                .roleName(createUserRoleDto.getRole())
                .build();

        userRoleRepository.save(userRole);
    }

    private void validateEmailExists(String email) {
        if (userAuthRepository.findByEmail(email) != null) {
            throw new AlreadyExistException("This email already exists");
        }
    }
}
