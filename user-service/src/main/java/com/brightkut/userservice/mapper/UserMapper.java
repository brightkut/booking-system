package com.brightkut.userservice.mapper;

import com.brightkut.userservice.dto.AddPaymentCardDto;
import com.brightkut.userservice.dto.RegisterUserDto;
import com.brightkut.userservice.dto.UpdateUserDto;
import com.brightkut.userservice.entity.UserAuth;
import com.brightkut.userservice.entity.UserProfile;
import com.brightkut.userservice.entity.UserRole;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

// componentModel = "spring" -> for create spring bean and inject via @Autowired
@Mapper(componentModel = "spring")
public interface UserMapper {

    // public UserAuth toUserAuth(RegisterUserDto registerUserDto, UserRole
    // userRole) {
    // var userProfile = UserProfile.builder()
    // .firstName(registerUserDto.getFirstName())
    // .lastName(registerUserDto.getLastName())
    // .dateOfBirth(registerUserDto.getDateOfBirth())
    // .phoneNumber(registerUserDto.getPhoneNumber())
    // .build();
    //
    // return UserAuth.builder()
    // .email(registerUserDto.getEmail())
    // .passwordHash(registerUserDto.getPassword())
    // .userProfile(userProfile)
    // .userRole(userRole)
    // .build();
    // }

    @Mapping(target = "email", source = "registerUserDto.email")
    @Mapping(target = "passwordHash", source = "registerUserDto.password")
    @Mapping(target = "verifyToken", source = "verifyToken")
    @Mapping(target = "userProfile.firstName", source = "registerUserDto.firstName")
    @Mapping(target = "userProfile.lastName", source = "registerUserDto.lastName")
    @Mapping(target = "userProfile.dateOfBirth", source = "registerUserDto.dateOfBirth")
    @Mapping(target = "userProfile.phoneNumber", source = "registerUserDto.phoneNumber")
    @Mapping(target = "userRole", source = "userRole")
    UserAuth toUserAuth(RegisterUserDto registerUserDto, UserRole userRole, String verifyToken);

//    @Mapping(target = "userAuth.userProfile.firstName", source = "updateUserDto.firstName")
//    @Mapping(target = "userAuth.userProfile.lastName", source = "updateUserDto.lastName")
//    @Mapping(target = "userAuth.userProfile.dateOfBirth", source = "updateUserDto.dateOfBirth")
//    @Mapping(target = "userAuth.userProfile.phoneNumber", source = "updateUserDto.phoneNumber")
//    UserAuth toUserAuth(UpdateUserDto updateUserDto, UserAuth userAuth);

    void updateUser(@MappingTarget UserAuth userAuth, UpdateUserDto updateUserDto);

    @AfterMapping
    default void updateNestedProfile(@MappingTarget UserAuth userAuth, UpdateUserDto dto) {
        if (userAuth.getUserProfile() == null) {
            userAuth.setUserProfile(new UserProfile());
        }

        userAuth.getUserProfile().setFirstName(dto.getFirstName());
        userAuth.getUserProfile().setLastName(dto.getLastName());
        userAuth.getUserProfile().setDateOfBirth(dto.getDateOfBirth());
        userAuth.getUserProfile().setPhoneNumber(dto.getPhoneNumber());
    }}
