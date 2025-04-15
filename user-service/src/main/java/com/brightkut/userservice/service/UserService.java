package com.brightkut.userservice.service;

import com.brightkut.commonlib.lib.exception.AlreadyExistException;
import com.brightkut.userservice.dto.CreateUserRoleDto;
import com.brightkut.userservice.dto.DetailEmailDto;
import com.brightkut.userservice.dto.RegisterUserDto;
import com.brightkut.userservice.entity.UserRole;
import com.brightkut.userservice.enums.RoleEnum;
import com.brightkut.userservice.mapper.UserMapper;
import com.brightkut.userservice.repository.UserAuthRepository;
import com.brightkut.userservice.repository.UserRoleRepository;
import com.brightkut.userservice.util.TokenUtil;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final EmailService emailService;
    private final UserAuthRepository userAuthRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;

    public UserService(EmailService emailService, UserAuthRepository userAuthRepository,
            UserRoleRepository userRoleRepository,
            UserMapper userMapper) {
        this.emailService = emailService;
        this.userAuthRepository = userAuthRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
    }

    public void registerUser(RegisterUserDto registerUserDto) {
        validateEmailExists(registerUserDto.getEmail());

        var userRole = userRoleRepository.findByRole(RoleEnum.NORMAL_USER);

        var token = TokenUtil.generateSaltToken(8);

        var userAuth = userMapper.toUserAuth(registerUserDto, userRole, token);

        userAuthRepository.save(userAuth);

        var emailDetail = DetailEmailDto.builder().recipient(registerUserDto.getEmail())
                .emailBody("Please use this token to verify email: ".concat(token))
                .emailSubject("Verify Email").build();

        emailService.sendSimpleMail(emailDetail);
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
