package com.brightkut.userservice.service;

import com.brightkut.commonlib.lib.exception.AlreadyExistException;
import com.brightkut.commonlib.lib.exception.BusinessException;
import com.brightkut.commonlib.lib.exception.UnAuthorizeException;
import com.brightkut.userservice.dto.AccessTokenDto;
import com.brightkut.userservice.dto.CreateUserRoleDto;
import com.brightkut.userservice.dto.DetailEmailDto;
import com.brightkut.userservice.dto.LoginDto;
import com.brightkut.userservice.dto.RegisterUserDto;
import com.brightkut.userservice.dto.VerifyEmailDto;
import com.brightkut.userservice.entity.UserAuth;
import com.brightkut.userservice.entity.UserRole;
import com.brightkut.userservice.enums.RoleEnum;
import com.brightkut.userservice.mapper.UserMapper;
import com.brightkut.userservice.repository.UserAuthRepository;
import com.brightkut.userservice.repository.UserRoleRepository;
import com.brightkut.userservice.util.TokenUtil;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final EmailService emailService;
    private final JwtService jwtService;
    private final UserAuthRepository userAuthRepository;
    private final UserRoleRepository userRoleRepository;
    private final StringRedisTemplate redisTemplate;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    public UserService(EmailService emailService, JwtService jwtService, UserAuthRepository userAuthRepository,
            StringRedisTemplate redisTemplate,
            UserRoleRepository userRoleRepository,
            UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.jwtService = jwtService;
        this.userAuthRepository = userAuthRepository;
        this.userRoleRepository = userRoleRepository;
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterUserDto registerUserDto) {
        validateEmailExists(registerUserDto.getEmail());

        var userRole = userRoleRepository.findByRole(RoleEnum.NORMAL_USER);

        var token = TokenUtil.generateSaltToken(8);

        registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        var userAuth = userMapper.toUserAuth(registerUserDto, userRole, token);

        userAuthRepository.save(userAuth);

        var emailDetail = DetailEmailDto.builder().recipient(registerUserDto.getEmail())
                .emailBody("Please use this token to verify email: ".concat(token))
                .emailSubject("Verify Email").build();

        emailService.sendSimpleMail(emailDetail);
    }

    public AccessTokenDto login(LoginDto loginDto) {
        var userAuth = userAuthRepository.findByEmail(loginDto.getEmail());


        validateUserAuthIsVerified(userAuth);

        validatePassword(loginDto, userAuth);

        var accessToken = jwtService.generateToken(userAuth);

        redisTemplate.opsForValue().set(userAuth.getUserAuthId().toString(), accessToken, Duration.ofMinutes(5));

        return AccessTokenDto.builder().accessToken(accessToken).build();
    }

    public void createUserRole(CreateUserRoleDto createUserRoleDto) {
        var userRole = UserRole
                .builder()
                .roleName(createUserRoleDto.getRole())
                .build();

        userRoleRepository.save(userRole);
    }

    public void verifyEmail(VerifyEmailDto verifyEmailDto) {
        var userAuth = userAuthRepository.findByEmail(verifyEmailDto.getEmail());

        validateEmailToken(verifyEmailDto, userAuth.getVerifyToken());

        userAuth.setIsVerified(true);

        userAuthRepository.save(userAuth);
    }

    private void validateEmailExists(String email) {
        if (userAuthRepository.findByEmail(email) != null) {
            throw new AlreadyExistException("This email already exists");
        }
    }

    private void validateEmailToken(VerifyEmailDto verifyEmailDto, String token) {
        if (!verifyEmailDto.getToken().equals(token)) {
            throw new BusinessException("Error occur invalid email token");
        }
    }

    private void validateUserAuthIsVerified(UserAuth userAuth) {
        if (Boolean.FALSE.equals(userAuth.getIsVerified())) {
            throw new UnAuthorizeException("Error occur when user is not verified");
        }
    }

    private void validatePassword(LoginDto loginDto, UserAuth userAuth){
        if (!passwordEncoder.matches(loginDto.getPassword(), userAuth.getPasswordHash())) {
            throw new UnAuthorizeException("Invalid email or password");
        }
    }
}
