package com.brightkut.userservice.service;

import com.brightkut.kei.exception.AlreadyExistException;
import com.brightkut.kei.exception.BusinessException;
import com.brightkut.kei.exception.UnAuthorizeException;
import com.brightkut.kei.util.EmailUtil;
import com.brightkut.kei.util.TokenUtil;
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
import com.brightkut.userservice.model.UserData;
import com.brightkut.userservice.repository.UserAuthRepository;
import com.brightkut.userservice.repository.UserRoleRepository;

import java.time.Duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final JwtService jwtService;
    private final UserAuthRepository userAuthRepository;
    private final UserRoleRepository userRoleRepository;
    private final StringRedisTemplate redisTemplate;
    private final UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final EmailUtil emailUtil;

    public UserService(JwtService jwtService, UserAuthRepository userAuthRepository,
                       StringRedisTemplate redisTemplate,
                       UserRoleRepository userRoleRepository,
                       UserMapper userMapper, PasswordEncoder passwordEncoder, ObjectMapper objectMapper, EmailUtil emailUtil) {
        this.jwtService = jwtService;
        this.userAuthRepository = userAuthRepository;
        this.userRoleRepository = userRoleRepository;
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
        this.emailUtil = emailUtil;
    }

    public void registerUser(RegisterUserDto registerUserDto) {
        validateEmailExists(registerUserDto.getEmail());

        var userRole = userRoleRepository.findByRole(RoleEnum.NORMAL_USER);

        var token = TokenUtil.generateSalt(8);

        registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        var userAuth = userMapper.toUserAuth(registerUserDto, userRole, token);

        userAuthRepository.save(userAuth);

        var emailDetail = EmailUtil.EmailDetail.builder().recipient(registerUserDto.getEmail())
                .emailBody("Please use this token to verify email: ".concat(token))
                .emailSubject("Verify Email").build();

        emailUtil.sendSimpleMail(emailDetail);
    }

    public AccessTokenDto login(LoginDto loginDto) {
        var userAuth = userAuthRepository.findByEmail(loginDto.getEmail());

        validateUserAuthIsVerified(userAuth);

        validatePassword(loginDto, userAuth);

        var refreshToken = jwtService.generateRefreshToken(userAuth);
        var accessToken = jwtService.generateToken(userAuth, refreshToken);

        // Set token to redis cache
        redisTemplate.opsForValue().set("ac".concat(userAuth.getEmail()).concat(accessToken), accessToken, Duration.ofMinutes(5));
        redisTemplate.opsForValue().set("rf".concat(userAuth.getEmail()).concat(accessToken), refreshToken, Duration.ofHours(2));

        return AccessTokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public void logout(String accessToken) {
        Claims claim = jwtService.extractAllClaims(accessToken);

        var email = claim.getSubject();

        var redisAccessToken = redisTemplate.opsForValue().get("ac".concat(email).concat(accessToken));

        if (redisAccessToken != null) {
            redisTemplate.delete("ac".concat(email).concat(accessToken));
        }

        var redisRefreshToken = redisTemplate.opsForValue().get("rf".concat(email).concat(accessToken));

        if (redisRefreshToken != null) {
            redisTemplate.delete("rf".concat(email).concat(accessToken));
        }
    }

    // use for verified access
    public void verifyAccessToken(String accessToken) {
        Claims claim = jwtService.extractAllClaims(accessToken);

        var email = claim.getSubject();
        validateToken(accessToken, userAuthRepository.findByEmail(email));

        var token = redisTemplate.opsForValue().get("ac".concat(email).concat(accessToken));

        if (token == null) {
            throw new UnAuthorizeException("Error occur when user does not login yet");
        }
    }

    public AccessTokenDto refreshToken(String accessToken) throws JsonProcessingException {
        Claims claim = jwtService.extractAllClaims(accessToken);

        String userMetadataStr = objectMapper.writeValueAsString(claim);

        UserData userDataRes =objectMapper.readValue(userMetadataStr, new TypeReference<UserData>() {});

        var email = claim.getSubject();
        var userAuth = userAuthRepository.findByEmail(email);
        validateToken(userDataRes.getRefreshToken(), userAuth);

        var token = redisTemplate.opsForValue().get("rf".concat(email).concat(accessToken));

        if (token == null) {
            throw new UnAuthorizeException("Error occur when user does not login yet or refresh token is expired");
        }
        redisTemplate.delete("ac".concat(email).concat(accessToken));
        redisTemplate.delete("rf".concat(email).concat(accessToken));

        var refreshToken = jwtService.generateRefreshToken(userAuth);
        var newAccessToken = jwtService.generateToken(userAuth, refreshToken);

        // Set token to redis cache
        redisTemplate.opsForValue().set("ac".concat(userAuth.getEmail()).concat(newAccessToken), newAccessToken, Duration.ofMinutes(5));
        redisTemplate.opsForValue().set("rf".concat(userAuth.getEmail()).concat(newAccessToken), refreshToken, Duration.ofHours(2));

        return AccessTokenDto.builder().accessToken(newAccessToken).refreshToken(refreshToken).build();
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

    private void validatePassword(LoginDto loginDto, UserAuth userAuth) {
        if (!passwordEncoder.matches(loginDto.getPassword(), userAuth.getPasswordHash())) {
            throw new UnAuthorizeException("Invalid email or password");
        }
    }

    private void validateToken(String token, UserAuth userAuth) {
       if(!jwtService.isTokenValid(token, userAuth)){
           throw new UnAuthorizeException("Invalid access or refresh token");
       }
    }
}
